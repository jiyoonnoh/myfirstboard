<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="v" value="<%=new java.util.Date().getTime()%>"/>

<title>Insert title here</title>

<script type="text/javascript" src="resources/js/join_check.js?v=${v }"></script>
<script type="text/javascript">
function validate(tag, pwd){
	var result = $('[name='+ tag +']').val();
	if( tag == 'userpwd'){
		result = join.pwd_status(result);
	}else if( tag == 'userpwd_ck'){
		result = join.pwd_ck_status(result, pwd);
	}
	$('#'+tag+'_status').text( result.desc );
	$('#'+tag+'_status').removeClass('valid').removeClass('invalid');
	$('#'+tag+'_status').addClass(result.code == 'valid' ? 'valid' : 'invalid');
	return result;
}
</script>
</head>
<body>
	<h3>비밀번호 바꾸기</h3>
	<form method="post" action="update.me">
		<input type="hidden" name="userid" value="${login_info.userid }" />
		<input type="hidden" name="name" value="${login_info.name }" />
		<input type="hidden" name="phone_number" value="${login_info.phone_number }" /> 
		<input type="hidden" name="admin" value="${login_info.admin } "/>
	 	
		<h5>현재 비밀번호</h5>
			<input type="password" name="userpwd1" /> 
		<h5>새 비밀번호</h5>
			<input onkeyup="validate('userpwd')" type="password" name="userpwd" />
		<h5>새 비밀번호 확인</h5>
			<input onkeyup="validate('userpwd_ck', $('[name=userpwd]').val() )"
				type="password" name="userpwd_ck" />
		<div class="valid" id="userpwd_ck_status">비밀번호를 다시 입력하세요.</div>
		
		<input type="button" onclick="go_update()" value="수정하기"> 
		<input type="button" onclick="history.go(-1)" value="취소">
	</form>	
	
	<script type="text/javascript">
	function go_update(){
		 if( $('[name=userpwd]').val() == '' )  ){
		alert('새 비밀번호를 입력하세요.');
		$('[name=userpwd]').focus();
		return;
		}	 
			if( ! item_check('userpwd') ) return;
			if( ! item_check('userpwd_ck', $('[name=userpwd]').val() ) ) return;
			
			if ( confirm('입력하신 정보가 맞습니까?') ){
				$('form').submit();
			}
		}
		
		function item_check(item, pwd){
			var result = validate(item, pwd);
			if( result.code != 'valid'){
				alert(result.desc);
				$('[name='+ item +']').focus();
				return false;
			}else
				return true;
		}
	</script>
	
</body>
</html>