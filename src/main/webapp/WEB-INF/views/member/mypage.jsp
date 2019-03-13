<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
</head>
<body>
<h3>마이페이지</h3>
<h5>${login_info.name }님, 안녕하세요.</h5>
<div id="container">
<input type="button" onclick="location='modify.me?userid=${login_info.userid}'" value="비밀번호 변경">
<input type="button" onclick="if ( confirm('정말 탈퇴하시겠습니까?') ){ location='delete.me?userid=${login_info.userid}' }" value="탈퇴하기">
<input type="button" onclick="history.go(-1)" value="뒤로가기">

</div>


</body>
</html>