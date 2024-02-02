$(function(){

//	let loginStatus = '${status}';
	let loginStatus = '${param.status }';
	if (loginStatus == 'fail'){
		alert("아이디 또는 비밀번호를 다시 확인 해주세요");
	}

});