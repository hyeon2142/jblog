<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    pageContext.setAttribute("newline", "\n");
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/blog-header.jsp" />
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<h4>${postVo.title }</h4>
					<p>
						${fn:replace(postVo.contents, newline, "<br/>") }
					</p>
				</div>
				<ul class="blog-list">
					<c:forEach items="${postList }" var="postVo" >
						<li>
							<a href="${pageContext.request.contextPath}/${blogId }/${postVo.categoryNo }/${postVo.no }">${postVo.title }</a> 
							<span>${postVo.regDate }</span>	
						</li>
				    </c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}${blogVo.logo }">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach items="${categoryList }" var="categoryVo" >
					<li>
						<a href="${pageContext.request.contextPath}/${blogId }/${categoryVo.no }">
							<c:choose>
								<c:when test='${categoryVo.name == "미분류" }'>
									기타
								</c:when>
								<c:otherwise>
									${categoryVo.name }
								</c:otherwise>
							</c:choose>
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
		
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp" />
	</div>
</body>
</html>