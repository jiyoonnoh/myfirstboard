var join = {
id: {
	empty: {
		code: 'empty',
		desc: '사용하실 ID를 입력해주세요(수신 가능 E-mail입력)'
	},
	valid: {
		code: 'valid',
		desc: '중복 확인을 해주세요.'
	},
	invalid: {
		code: 'invalid',
		desc: '유효하지 않은 이메일 형식입니다.'
	},
	usable: {
		code: 'usable',
		desc: '사용가능한 아이디입니다'
	},
	unusable: {
		code: 'unusable',
		desc: '조회하신 아이디는 중복되는 아이디입니다.'
	}
},
id_status: function(id){
	var space = /\s/g;
	var reg = /[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
	if( id == '' ){
		return this.id.empty;
	}else if( id.match(space) ){
		return this.id.space;
	}else if( reg.test(id) ){
		return this.id.valid;
	}else{
		return this.id.invalid;
	}
},

id_check(usable){
	if( usable=='true'){
		return this.id.usable;
	}else
		return this.id.unusable;
},

pwd : {
	empty: {
		code: 'empty',
		desc: '비밀번호(영문+숫자+특수문자 조합 8-16자리 이내)를 입력하세요.'
	},
	space : {
		code: 'space',
		desc: '공백없이 입력하세요'
	},
	valid :{
		code: 'valid',
		desc: '사용가능한 비밀번호입니다'
	},
	invalid :{
		code: 'invalid',
		desc: '비밀번호(영문+숫자+특수문자 조합 8-16자리 이내)를 입력하세요.'
	},
	lack : {
		code : 'lack',
		desc : '비밀번호(영문+숫자+특수문자 조합 8-16자리 이내)를 입력하세요.'
	}
},

pwd_status(pwd){
// 영문대,소문자, 숫자를 모두 포함
	var space = /\s/g;
	var reg = /[^a-zA-Z0-9]/g;
	var digit = /[0-9]/g;
	var upper = /[A-Z]/g;
	var lower = /[a-z]/g;
	if( pwd=='' ){
		return this.pwd.empty;
	}else if( pwd.match(space) ){
		return this.pwd.space;
	}else if( reg.test(pwd) ){
		return this.pwd.invalid;
	}else if( pwd.length < 7 ){
		return this.pwd.min;
	}else if( pwd.length > 16 ){
		return this.pwd.max;
	}else if( !lower.test(pwd) || !upper.test(pwd) 
								    || !digit.test(pwd) ){
		return this.pwd.lack;
	}else{
		return this.pwd.valid;
	}
},
pwd_ck:{
	empty: {
		code: 'empty',
		desc: '비밀번호를 다시 입력하세요'
	},
	valid: {
		code: 'valid',
		desc: '비밀번호가 일치합니다'
	},
	invalid: {
		code: 'invalid',
		desc: '비밀번호가 일치하지 않습니다'
	}
},
pwd_ck_status: function(pwd_ck, pwd){
	
	if(pwd_ck == '' ){
		return this.pwd_ck.empty;
	}else if( pwd_ck == pwd ){
		return this.pwd_ck.valid;
	}else{
		return this.pwd_ck.invalid;
	}
}

}





