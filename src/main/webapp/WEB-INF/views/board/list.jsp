<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div style=" width:1000px; margin: 0 auto;">
	<h3>게시판</h3>
	<form method="post" action="list.bo" id="list" name="list">
		<input type="hidden" name="curPage" />
		<p id="list-top">
			<span style="float: center;"> 
			<select name="search"style="height: 28px; width: 100px;">
					<option value="all" ${page.search eq 'all' ? 'selected' : '' }>전체</option>
					<option value="title" ${page.search eq 'title' ? 'selected' : '' }>제목</option>
					<option value="content"
						${page.search eq 'content' ? 'selected' : '' }>내용</option>
					<option value="writer"
						${page.search eq 'writer' ? 'selected' : '' }>작성자</option>
			</select> 
			<input name="keyword" value="${page.keyword }" type="text"
				style="vertical-align: top; width: 400px;" /> 
			<a class="btn-fill"	onclick="$('form').submit()">검색</a>
			</span>

			
			</p>
	</form>
	<c:if test="${!empty login_info }">
				<input type="button" value="등록" onclick="location='new.bo'"
					style="float: right;">
				<br>
			</c:if>
	<table border="1">
		<tr>
			<th>제목</th>
			<th style="width: 15%;">등록일</th>
			<th style="width: 15%;">작성자</th>
		</tr>
		<c:forEach items="${page.list}" var="vo">
			<tr>
				<td class="left"><a onclick="location='detail.bo?id=${vo.id}&flag=1'">${vo.title }</a></td>
				<td style="width: 15%;">${vo.writedate }</td>
				<td>${vo.writer }</td>
			</tr>
		</c:forEach>
	</table><br>
	<jsp:include page="/WEB-INF/views/include/page.jsp" />
	</div>
</body>
</html>