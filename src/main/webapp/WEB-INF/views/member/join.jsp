<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
<c:set var="v" value="<%=new java.util.Date().getTime()%>"/>
<script src="https://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="resources/js/join_check.js?v=${v}"></script>
</head>
<body>
<form method="post" action="join">
	<table border="1">
		<tr>
			<th>이름</th>
			<td><input name="name" type="text" /></td>
		</tr>
		<tr>
			<th>아이디</th>
			<td><input name="userid" type="text" /></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input name="userpwd" type="password" /></td>
		</tr>
		<tr>
			<th>비밀번호 확인</th>
			<td><input name="userpwd_ck" type="password" /></td>
		</tr>
		<tr>
			<th>전화번호(-제외)</th>
			<td><input name="phone_number" type="text" /></td>
		</tr>
	</table>
	
	<a onclick="go_join()"><input type="button" value="가입"></a>
	<input type="reset" value="다 지우기">
</form>

<script type="text/javascript">
	function go_join() {
		
		
	}
</script>

</body>
</html>
