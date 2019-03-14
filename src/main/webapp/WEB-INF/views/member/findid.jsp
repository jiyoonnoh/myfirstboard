<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function find_id() {
	if ($('[name=username]').val() == '') {
		alert('이름를 입력하세요.');
		return;
	}
	
	var sendData = $('#form').serialize();
// 	console.log(sendData);
	
	// DB에 존재여부를 판단
	$.ajax({
		type : 'post',
		url : '/app/id_find2',
		dataType: "json",
		data : {
			name : jQuery("input[name='username']").val()
		},
		success : function(data) {
			
		
			alert("회원님의 아이디는"+data.bean.userid+"입니다.");
			
			console.log("#########################");
			
			console.log(data.bean);
			
			console.log("#########################");
			
			console.log(data.bean.userid);
			
			console.log("#########################");
			
			
			
		},
		error : function(req, status) {
			alert(status + ': ' + req.status);
		}
	});
	
}
</script>
</head>
<body>

<h2>아이디 찾기</h2>
<h6>이름을 입력하세요.</h6>
<form id="form" name="form">
<input type="text" id="name" name="username" required="required">
<input type="hidden" id="test" name="test" value="11" required="required">

<button type="button" id="findbtn" name="username" onclick="find_id()">찾기!</button>
<input type="button" onclick="history.go(-1)" value="뒤로가기">
</form>
</body>
</html>