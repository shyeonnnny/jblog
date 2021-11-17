<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="header">
	<c:choose>
		<c:when test="${empty authUser }">
			<h1>스프링블로그</h1>
			<ul>
				<li><a href="${pageContext.request.contextPath}/views/user/login.jsp">로그인</a></li>
			</ul>
		</c:when>
		<c:otherwise>
			<h1>${blog.title }</h1>
			<ul>
				<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
				<li><a href="${pageContext.request.contextPath}/blog/blog-admin-basic">블로그 관리</a></li>
			</ul>
		</c:otherwise>
	</c:choose>
</div>	

