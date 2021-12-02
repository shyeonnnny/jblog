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
				dataType: 'json', //Ajax를 통해 호출한 페이지의 Return 형식
				contentType: 'application/json', // 전송할 데이터 타입
				data: JSON.stringify(vo), // url 호출시 보낼 파라미터 타입 (contentType이랑 data는 맞아야함)
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

		// on으로 해주는이유는 fetchcategory()가 비동기라서 실행되는 시점이 랜덤이기때문에
		// click이벤트가 먼저 달릴수도있고 안달릴수도있어서 on을써주면 미리 다달아주고 새로등록된곳에도
		// 달아주기때문에 on을 쓴다 (live와 같은 기능)
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
				<li><a href="${pageContext.request.contextPath}/views/user/login.jsp">로그인</a></li>
				<li><a href="${pageContext.request.contextPath}/views/main/index.jsp">로그아웃</a></li>
				<li><a href="${pageContext.request.contextPath}/blog/${blog.id }">홈</a></li>
			</ul>
		</div>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
				
					<li><a href="${pageContext.request.contextPath}/blog/${blog.id }/blog-admin-basic">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a href="${pageContext.request.contextPath}/blog/${blog.id }/blog-admin-write">글작성</a></li>
				</ul>
		      	<table class="admin-cat">
		      		<tr>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>
		      			     			
		      		</tr>
		
		      		
									  
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
      			<form id="add-cate" method="post" action="${pageContext.request.contextPath }/blog/${blog.id }/categoryadd">
		      	<table id="admin-cat-add">
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td><input id="input-name" type="text" name="name"></td>
		      		</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td><input id="input-desc" type="text" name="desc"></td>
		      		</tr>
		      		<tr>
		      			<td class="s">&nbsp;</td>
		      			<td><input type="submit" value="카테고리 추가"></td>
		      		</tr>      		      		
		      	</table> 
		      	</form>
			</div>
		</div>
		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>