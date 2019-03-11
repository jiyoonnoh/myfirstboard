<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h3>게시판</h3>
	<table border="1">
		<tr>
			<th style="width: 15%;">제목</th>
			<td class="left" colspan="5">${vo.title }</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${vo.writer }</td>
			<th style="width: 15%;">작성일자</th>
			<td style="width: 15%;">${vo.writedate }</td>
			<th style="width: 15%;">조회수</th>
			<td style="width: 10%;">${vo.readcnt }</td>
		</tr>
		<tr>
			<th>내용</th>
			<td class="left" colspan="5">${fn: replace(vo.content, crlf, '<br>') }</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td class="left" colspan="5">${vo.filename }<c:if
					test="${ !empty vo.filename }">
					<input onclick="location='download.bo?id=${vo.id}'" type="button"
						value="다운로드" />
				</c:if>
			</td>
		</tr>
	</table>
	<br>
	<!-- 로그인된 사용자만 답글작성 가능 -->
	<c:if test="${!empty login_info }">
		<a class="btn-fill" onclick="location='reply.bo?id=${vo.id}'">답글쓰기</a>
	</c:if>

	<!-- 관리자로 로그인한 경우만 수정,삭제 가능 -->
	<c:if test="${login_info.admin eq 'Y' }">
		<a class="btn-fill" onclick="location='modify.bo?id=${vo.id}'">수정</a>
		<a class="btn-fill"
			onclick="if( confirm('정말 삭제하시겠습니까?') ){ location='delete.bo?id=${vo.id}' }">삭제</a>
	</c:if>

	<a class="btn-fill" onclick="$('#detail').submit()">목록으로</a>
	<form method="post" id="detail" action="list.bo">
		<input type="hidden" name="curPage" value="${page.curPage }" /> <input
			type="hidden" name="search" value="${page.search }" /> <input
			type="hidden" name="keyword" value="${page.keyword }" />
	</form>

</body>
</html>