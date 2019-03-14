<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="v" value="<%=new java.util.Date().getTime()%>"/>

<title>Insert title here</title>

<script type="text/javascript" src="resources/js/join_check.js?v=${v }"></script>
<script type="text/javascript">
	function validate(tag, pwd){
		
		var result = $('[name='+ tag +']').val();
			
		if( tag == 'userpwd_ck'){
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

		<h5>새 비밀번호</h5>
			<input type="password" name="userpwd" />
		<h5>새 비밀번호 확인</h5>
			<input onkeyup="validate('userpwd_ck', $('[name=userpwd]').val() )" 
				type="password" name="userpwd_ck" />
		<div class="valid" id="userpwd_ck_status">비밀번호를 다시 입력하세요.</div>
		
	<a onclick="go_update()"><input type="button"  value="수정하기"/></a>
	<a onclick="history.go(-1)"><input type="button" value="취소"/></a>
</form>
	
	<script type="text/javascript">
	function go_update(){
		 if( $('[name=userpwd]').val() == '' ){
			alert('비밀번호를 입력하세요.');
			$('[name=userpwd]').focus();
			return;
		}	 
			//비번,비번확인에 대한 유효성확인	
// 			if( ! item_check('userpwd') ) return;
			if( ! item_check('userpwd_ck', $('[name=userpwd]').val() ) ) return;
			
			if ( confirm('입력하신 정보가 맞습니까?') ){
				$('form').submit();
				alert('비밀번호가 변경되었습니다. 새로운 비밀번호로 다시 로그인해주세요.');
			}
		}
	function item_check(item, pwd){
		
		console.log()
		
		var result = validate(item, pwd);

		console.log(result);
		
		if( result.code != 'valid'){
			alert(result.desc);
			$('[name='+ item +']').focus();
			return false;
			
		}else{
			return true;
		}
	}
		
	
	</script>
	
</body>
</html>