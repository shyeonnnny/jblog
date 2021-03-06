<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/asset
s/css/jblog.css">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script>
	var render = function(vo, index, size){
		var html =
			"<tr data-no='" +vo.no + "'>" +
				"<td>" +vo.name + "</td>" +
				"<td>" + vo.postcount + "</td>" +
				"<td>" +vo.desc + "</td>"
				
		if(size == 1){
			html += "<td>" +
			"<img src='${pageContext.request.contextPath}/assets/images/delete.jpg' class='rm-cate'></td>" 
		} else {
			html += "<td>" +
			"<a href='${pageContext.request.contextPath }/blog/${blog.id }/delete/" + vo.no + "'" + ">" +
			"<img data-no='" +vo.no + "' src='${pageContext.request.contextPath}/assets/images/delete.jpg' class='rm-cate'></a></td>" 
		}
		html += "</tr>"
		
		return html;
	}
	
	var fetchcategory = function(){
		$.ajax({
			url: '${pageContext.request.contextPath}/blog/${blog.id}/catelist',
			type: 'get',
			dataType: 'json',
			data: '',
			success: function(response){
				if(response.result != "success"){
					console.error(response.message);
					return;
				}
				
				var categorylist = "";
				for (var i=0; i<response.data.length; i++){
					var html = render(response.data[i],i+1, response.data.length);
					categorylist += html;
				}
				console.log(categorylist);
				
				$(".admin-cat").append(categorylist);
			}
			
		})
	}
	
	$(function(){
		$('#add-cate').submit(function(event){
			event.preventDefault();
			
			var vo = {};
			vo.name = $("#input-name").val();
			vo.desc = $("#input-desc").val();
			
			$.ajax({
				url: '${pageContext.request.contextPath }/blog/${blog.id}/cateadd',
				type: 'post',
				dataType: 'json', //Ajax??? ?????? ????????? ???????????? Return ??????
				contentType: 'application/json', // ????????? ????????? ??????
				data: JSON.stringify(vo), // url ????????? ?????? ???????????? ?????? (contentType?????? data??? ????????????)
				success: function(response){
					if(response.result != "success"){
						console.error(response.message);
						return;
					}
					
					var html = render(response.data, document.getElementsByClassName('index').length + 1, response.data.length);
					$(".admin-cat").append(html);
					
				}
				
			})
		})

		// on?????? ?????????????????? fetchcategory()??? ??????????????? ???????????? ????????? ?????????????????????
		// click???????????? ?????? ?????????????????? ???????????????????????? on???????????? ?????? ??????????????? ????????????????????????
		// ????????????????????? on??? ?????? (live??? ?????? ??????)
		$(".admin-cat").on("click", ".rm-cate",function(event){
			event.preventDefault();
			var cateno = $(event.target).data("no")
			a = event.target;
			
			console.log(cateno);
			console.log($(event.target).data("no"))
			
			$.ajax({
				url: '${pageContext.request.contextPath}/blog/${blog.id}/catedelete/' + cateno,
				type: 'delete',
				dataType: 'json',
				success: function(response){
					if(response.result != "success"){
						console.error(response.message);
						return;
					}
					
					$('tr[data-no=' + $(a).data("no") + ']').remove()
					console.log(a.parentNode)
				}
				})
			
		})
		fetchcategory();
	})
	
	
	
	
</script>
</head>
<body>
	<div id="container">
		<div id="header">
			<h1>${blog.title }</h1>
			<ul>
				<li><a href="${pageContext.request.contextPath}/views/user/login.jsp">?????????</a></li>
				<li><a href="${pageContext.request.contextPath}/views/main/index.jsp">????????????</a></li>
				<li><a href="${pageContext.request.contextPath}/blog/${blog.id }">???</a></li>
			</ul>
		</div>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
				
					<li><a href="${pageContext.request.contextPath}/blog/${blog.id }/blog-admin-basic">????????????</a></li>
					<li class="selected">????????????</li>
					<li><a href="${pageContext.request.contextPath}/blog/${blog.id }/blog-admin-write">?????????</a></li>
				</ul>
		      	<table class="admin-cat">
		      		<tr>
		      			<th>???????????????</th>
		      			<th>????????? ???</th>
		      			<th>??????</th>
		      			<th>??????</th>
		      			     			
		      		</tr>
		
		      		
									  
				</table>
      	
      			<h4 class="n-c">????????? ???????????? ??????</h4>
      			<form id="add-cate" method="post" action="${pageContext.request.contextPath }/blog/${blog.id }/categoryadd">
		      	<table id="admin-cat-add">
		      		<tr>
		      			<td class="t">???????????????</td>
		      			<td><input id="input-name" type="text" name="name"></td>
		      		</tr>
		      		<tr>
		      			<td class="t">??????</td>
		      			<td><input id="input-desc" type="text" name="desc"></td>
		      		</tr>
		      		<tr>
		      			<td class="s">&nbsp;</td>
		      			<td><input type="submit" value="???????????? ??????"></td>
		      		</tr>      		      		
		      	</table> 
		      	</form>
			</div>
		</div>
		<div id="footer">
			<p>
				<strong>Spring ?????????</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>