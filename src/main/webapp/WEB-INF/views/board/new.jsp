<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
</style>
<body>
<h3>게시글 작성하기</h3>
<div id=container style="text-align: center;">
<form method="post" action="insert.bo" enctype="multipart/form-data">
<table border="1" style="width: 800px;">
<tr>
<th>공지사항 선택<th>
      <label><input type="radio" value="notice"/>공지사항</label>
       <label><input type="radio" value="ordinary"/>일반</label>
</tr>
<tr><th>제목</th><td><input class="need" type="text" name="title" /></td></tr>
<tr><th>내용</th>
<td><textarea class="need" rows="5" cols="30" name="content"></textarea></td></tr>
<tr><th>썸네일 이미지 등록</th><td></td></tr>
<tr><th>첨부파일</th><td><input type="file" name="file"/></td></tr>
<tr><th>공개여부</th><td>

      <label><input type="radio" value="notice"/>공개</label>
       <label><input type="radio" value="ordinary"/>비공개</label>
</td></tr>
</table>
</form>
<a onclick="if( necessary() ) { $('form').submit() }" class="btn-fill">저장</a>
<a onclick="location='list.bo'" class="btn-empty">취소</a>
<script type="text/javascript" src="js/need_check.js"></script>
</div>
</body>
</html>