<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function find_id() {
	if($('[name=username]').val() == ''){
		alert('이름을 입력하세요');
		return;
	}
	
	//입력한 이름을 db에서 확인
	$.ajax({
		type: 'post',
		url: 'find_id',
		data: {
			username : $('[name=username]').val()
		},
		success: function (data) {
			var 
		}
	});
}
</script>
</head>
<body>
<h2>아이디 찾기</h2>
<h6>이름을 입력하세요.</h6>
<input type="text" name="name" required="required">
<input type="button" onclick="find_id()" name=username id=find-btn value="찾기!">
<input type="button" onclick="history.go(-1)" value="뒤로가기">
</body>
</html>