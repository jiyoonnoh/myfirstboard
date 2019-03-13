<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function find_pwd() {
	if ($('[name=userid]').val() == '') {
		alert('이름를 입력하세요.');
		return;
	}
	
	var sendData = $('#form').serialize();
// 	console.log(sendData);
	
	// DB에 존재여부를 판단
	$.ajax({
		type : 'post',
		url : '/app/pwd_find',
		dataType: "json",
		data : {
			userid : jQuery("input[name='userid']").val()
		},
		success : function(data) {
			
			console.log(data);
			alert("회원님의 비밀번호는"+data.bean.userpwd+"입니다.");
			
			console.log("#########################");
			
			console.log(data.bean);
			
			console.log("#########################");
			
			console.log(data.bean.userid);
			
			console.log("#########################");
			
			
// 			if(data == ''){
// 				alert("등록된 아이디가 없습니다.");
// 			} else {
// 				alert("고객님의 아이디는 : "+data+" 입니다.");
// 			}
			
		},
		error : function(req, status) {
			alert(status + ': ' + req.status);
		}
	});
	
}
</script>
</head>
<body>

<h2>비밀번호 찾기</h2>
<h6>아이디를 입력하세요.</h6>
<form id="form" name="form">
	<input type="text" id="uid" name="userid" required="required">

	<button type="button" id="findbtn" name="userid" onclick="find_pwd()">찾기!</button>
	<input type="button" onclick="history.go(-1)" value="뒤로가기">
</form>
</body>
</html>