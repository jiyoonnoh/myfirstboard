<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<c:set var="v" value="<%=new java.util.Date().getTime()%>" />
<link rel="stylesheet"	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="js/join_check.js?v=${v}"></script>
<script type="text/javascript">
	function validate(tag, pwd) {
		var result = $('[name=' + tag + ']').val();
		if (tag == 'userid') {
			result = join.id_status(result);
		} else if (tag == 'userpwd') {
			result = join.pwd_status(result);
		} else if (tag == 'userpwd_ck') {
			result = join.pwd_ck_status(result, pwd);
		}
		$('#' + tag + '_status').text(result.desc);
		$('#' + tag + '_status').removeClass('valid').removeClass('invalid');
		$('#' + tag + '_status').addClass(
				result.code == 'valid' ? 'valid' : 'invalid');
		return result;
	}
	function usable() {
		//입력한 아이디의 DB에 존재여부를 판단
		$.ajax({
			type : 'post',
			url : 'id_check',
			data : {
				userid : $('[name=userid]').val()
			},
			success : function(data) {
				var result = join.id_check(data);
				$('#userid_status').text(result.desc);
				$('#userid_status').addClass(
						result.code == 'usable' ? 'valid' : 'unvalid' );
				$('[name=id_check]').val(result.code);
			},
			error : function(req, status) {
				alert(status + ': ' + req.status);
			}
		});
	}
	//jiyoonnoh@bluewaves.co.kr
</script>
<style type="text/css">
#wrap {
	position: relative;
}

#header {
	width: 100%
}

#container{
width: 40%;
margin: 0 auto;}


</style>
</head>

<body>

	<h4 style="text-align: center;">회원가입</h4>
	<!-- 회원가입 폼 -->
	<div id="container">
		<div style="text-align: center;">
		<form method="post" action="join" >
			<input onkeyup="$('[name=id_check]').val('n'); validate('userid')"
				type="text" name="userid" style="width: 424px; height: 30px;"
				placeholder="사용하실 ID를 입력해주세요(수신 가능 E-mail입력)" /> 
			<input id="btn_id_check" onclick="usable()" type="button" value="확인" /> <br>
			<input onkeyup="validate('userpwd')" type="password" name="userpwd"
				style="width: 500px; height: 30px;"
				placeholder="비밀번호(영문+숫자+특수문자 조합 8-16자리 이내)" /><br> <input
				onkeyup="validate('userpwd_ck', $('[name=userpwd]').val() )"
				type="password" name="userpwd_ck"
				style="width: 500px; height: 30px;" placeholder="비밀번호 확인" /><br>
			<input name="name" style="width: 500px; height: 30px;" type="text"
				placeholder="이름을 입력해주세요." /><br> <input name="phone_number"
				style="width: 500px; height: 30px;" type="text"
				placeholder="휴대폰 번호 '-'없이 입력해주세요." /><br>
		</div>
			<small>※ 개인 추가정보는 회원가입 후 등록이 가능합니다.</small>

			<br><br>

			<!-- 이용약관 -->
			<table
				style=" height: 250px; border: 2px solid black; text-align: center;">
				<td>이용약관
				<td>
			</table>
			<input type="checkbox" name="chk_necessary1" value="이용약관에동의"> <small
				style="font-weight: bold;">약관에 동의합니다.(필수)</small><br>
			<br>
			<table
				style="height: 250px; border: 2px solid black; text-align: center;">
				<td>개인정보 이용약관
				<td>
			</table>
			<input type="checkbox" name="chk_necessary2" value="정보수집에동의"> <small
				style="font-weight: bold;">개인정보 수집 이용동의.(필수)</small><br> <small>※
				약관 및 개인정보 처리방침은 홈페이지 하단에 전문이 게재되어 있습니다. <br>※ 이용약관 및 개인정보 수집,
				이용 내용에 대해 동의, 거부가 가능하며, 이 경우 회원 가입 및 관련 서비스는 이용이 불가합니다.
				<br><br><br><br>
			</small> <br> <input type="checkbox" name="chk_email" value="email수신동의">
			<small style="font-weight: bold;">E-mail 수신 동의(선택)</small><br> <input
				type="checkbox" name="chk_sms" value="sms수신동의"> <small
				style="font-weight: bold;">sms 수신 동의(선택)</small><br>
			<br>
			<br> 
			<a class="btn-fill" onclick="go_join()">회원가입</a>
			<a class="btn-empty" onclick="history.go(-1)">취소</a> 
		<input type="hidden" name="id_check" value="n"/>
		</form>

		<script type="text/javascript">
			function go_join() {
				if (  $('input:checkbox[name=chk_necessary1]').is(":checked") == false ){
					alert("이용약관에 동의하지 않으셨습니다. 이용동의를 하셔야 회원가입을 할 수 있습니다.")
					return;
				}				
				if (  $('input:checkbox[name=chk_necessary2]').is(":checked") == false ){
					alert("개인정보 이용약관에 동의하지 않으셨습니다. 이용동의를 하셔야 회원가입을 할 수 있습니다.")
					return;
				} 
				if ($('[name=name]').val() == '') {
					alert('이름을 입력하세요.');
					$('[name=name]').focus();
					return;
				}

				//아이디,비번,비번확인에 대한 유효성확인
				//아이디는 중복확인 안 한 경우와 한 경우로 나누어 처리한다.
				if ($('[name=id_check]').val() == 'n') {
					//유효한 아이디인지부터 확인
					if (!item_check('userid'))
						return;
					else {
						alert(join.id.valid.desc); //유효한 조합의 아이디는 중복확인 유도
						$('#btn_id_check').focus();
						return;
					}
				} else if ($('[name=id_check]').val() == 'unusable') {
					alert(join.id.unusable.desc);
					$('[name=userid]').val('');
					$('[name=userid]').focus();
					return;
				}
				
				if (!item_check('userpwd'))
					return;
				if (!item_check('userpwd_ck', $('[name=userpwd]').val()))
					return;

				$('form').submit();
			}

			function item_check(item, pwd) {
				var result = validate(item, pwd);
				if (result.code != 'valid') {
					alert(result.desc);
					$('[name=' + item + ']').focus();
					return false;
				} else
					return true;
			}
		</script>
</div>


	<div id="footer"></div>

</body>
</html>


