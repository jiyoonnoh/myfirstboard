<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css">
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
	<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<style>
.category  { font-weight: bold;  color: red;}

table {
 border: 1px solid black; width: 100%; height: 70px; border-collapse: collapse;
}

ul li {list-style: none;}
a{color: black; text-decoration: none; cursor: pointer;}

</style>   

<script>
/* 창열기 버튼 클릭했을 때 */
$(function () {
	$("#dialog").dialog({
		autoOpen:false,
		position:"center",
		modal:false,
		resizable:false,
		width:490, height:"auto"		
			});
	
	$("#btn").on("click",function(){
		$("#dialog").dialog("open");
	});
});

function go_login(){
	$.ajax({
		type: 'post',
		url: 'login',
		data: { userid:$('#userid').val(), userpwd: $('#userpwd').val() },
		success: function(data){
			if( data=="true" ){
				alert("안녕하세요.")
				location.href= "index";
			}else{
				alert("아이디나 비밀번호를 다시 확인하세요.");
			}
		},
		error: function(req, text){
			alert( req.status +" : " +text);
		}
	});
}

function go_logout(){
	$.ajax({
		url: 'logout',
		success: function(){
			location.reload();
		},
		error: function(req, text){
			alert( req.status +" : " +text);
		}
	});
}
</script>

<div id="header">
<div align="right">
<c:if test="${ empty login_info }">
	<ul>
	<li><a id="btn">로그인</a>
	│	<a href="#">마이페이지</a><li>
	</ul>
</c:if>	 

<c:if test="${ !empty login_info }">
	<ul>
	<li><a id="logout" onclick="go_logout()">로그아웃</a> │
		<a href="#">마이페이지</a><li>
	</ul>
</c:if>	 
</div>

<table border="1px;" >
<tr>
<th width="15%"><a href= "index">로고</a></th>
<th><a href="list.bo">GNB</a>
</th>
</tr>
</table>
</div>
<div id="dialog" title="로그인하기">

	<input style="margin-bottom: 10px; width: 450px; height: 30px;"
		type="text" id="userid" placeholder="아이디" /> <input
		onkeypress="if(event.keyCode==13) go_login()"
		style="margin-bottom: 7px; width: 450px; height: 30px;"
		type="password" id="userpwd" placeholder="비밀번호" /> 
		<input type="checkbox" name="chk_info" value="로그인 유지하기">
		<small>로그인	유지하기</small>
		<small style="float: right;"><a onclick="location='findid'">아이디 찾기</a> / 비밀번호 찾기</small>
	<button style="margin-bottom: 10px; width: 450px; height: 35px;">
		<a onclick="go_login()">로그인</a>
	</button>
	
	
	<!-- 카카오 로그인 -->
<a id="kakao-login-btn" href="javascript:loginWithKakao()" style="margin: 0 auto;"></a>
<input type="hidden" name="id_check" value="n"/>
	<script type='text/javascript'>
		// 사용할 앱의 JavaScript 키를 설정해 주세요.
		Kakao.init('5b50ce8721b7c9551973fcf6db95adb9');
		// 카카오 로그인 버튼을 생성합니다.
		Kakao.Auth.createLoginButton({
			container : '#kakao-login-btn',
			success : function(authObj) {
				//로그인 성공 시, API 호출
				Kakao.API.request({
					url : '/v2/user/me',
					success : function(res) {
						var userid = res.id;
						var name = res.properties.nickname;
						//alert(userid+','+name);
						$.ajax({
							type : 'post',
							url : 'klogin',
							data : {
								userid : userid,
								name : name
							},
							success : function(data) {
								// db에 아이디 존재 여부 파악
								if (data == "true") {
									alert('안녕하세요. 카카오 로그인 성공.')
									location.href = "index";
								} else {
									//가입 처리 실패
									alert("로그인 실패, 관리자에게 문의하세요.")
								}
							}
						});
					},
					fail : function(err) {
						alert(JSON.stringify(err));
					}
				});
			},
			fail : function(err) {
				alert(JSON.stringify(err));
			}
		});
	</script>
	<!-- 카카오로그인 끝 -->
	
	<br>
	<small>회원이 아니신가요? <a onclick="location='member'"
		style="color: black; font-weight: bold; text-decoration: underline;">회원가입</a>
	</small>
	
	
	
</div>









