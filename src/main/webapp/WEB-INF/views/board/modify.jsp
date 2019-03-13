<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>게시판</h3>
	<form method="post" action="update.bo" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${vo.id }" />
		<table border="1">
			<th>공지사항 선택
			<th><input type="radio" name="noticeyn" value="1" id="notice" />
				<label for="notice">공지사항</label> <input type="radio" name="noticeyn"
				checked value="0" id="ordinary" /> <label for="ordinary">일반
			</label>
			</tr>
			<tr>
				<th>제목</th>
				<td><input value="${vo.title }" class="need" title="제목"
					style="width: 98.5%;" type="text" name="title" /></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td class="left">${vo.writer }</td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea class="need" title="내용" name="content"
						style="width: 99%; height: 200px;">${vo.content }</textarea></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td class="left"><c:if test="${ !empty vo.filename }">
						<original> <a><img id="delete"
							style="vertical-align: middle;" src="img/delete.png" /></a>&nbsp;&nbsp;${vo.filename }</original>
					</c:if> <input name="file" type="file" onchange="file_change( this )" /></td>
			</tr>
		</table>
		<br> <a onclick="if( necessary() ) { $('form').submit() }"
			class="btn-fill">저장</a> <a onclick="location='list.bo'"
			class="btn-empty">취소</a> <input type="hidden" name="attach" />
		<!-- 첨부파일삭제여부 -->
	</form>
	<script type="text/javascript">
		function file_change(f) {
			//새로운 파일을 선택하면 원래있던 파일명 안보이게
			if ($(f).val() != '')
				$('original').html('');
			else {
				//취소를 선택하면 원래 파일명 보이게
				//삭제이미지도 보이게
				var tag = '<a><img id="delete" style="vertical-align: middle;" ' 
		    + 'src="img/delete.png" /></a>&nbsp;&nbsp;';
				$('original').html(tag + '${vo.filename}');
			}
		}
	</script>
	<script type="text/javascript">
		$(function() {
			$(document).on('click', '#delete', function() {
				$('original').html('');
				$('[name=attach]').val('n');
			});
		});
	</script>
	<script type="text/javascript" src="js/need_check.js"></script>

</body>
</html>