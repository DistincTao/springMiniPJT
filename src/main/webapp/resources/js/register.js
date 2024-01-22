let userEmailValid = false

$(function() {

	// 아이디 유효성 검사
	$("#userId").blur (function(){
		validUserId();
	});
	
	// 비밀번호 유효성 검사
	$("#userPwd").change (function (){
		validUserPwd();
	})
	// 비밀 번호 일치 여부 검사
	$("#userPwd2").change (function (){
		matchingPwd();
	})
	
	// 이메일 등록 시
	$("#userEmail").change (function (){
		validEmail();
	})

	// 이메일 인증 버튼 클릭 시
	$("#sendEmailBtn").click (function (){
		if ($("#userEmail").val() != "") {
			let tmpUserEmail = $("#userEmail").val();

			$.ajax({
			url : 'sendEmail.mem',
			type : 'get',
			data : {"tmpUserEmail" : tmpUserEmail},
			dataType : 'json',
			async: false,
			success : function(data) {
				console.log(data);
				if (data.status == "success"){
					alert("이메일 전송 완료");
				} else if (data.status == "fail"){
				 // 발송 실패
					alert("이메일 발송 실패");
				}
			},
			error : function() {
			},
			complete : function() {
			}
		});
			
			$(".codeDiv").show();
			
		} else {
			printErrMsg ("userEmail", "이메일 주소를 입력하고 인증 버튼을 누르세요", true);
		}
	})
	
	$(".confirmCode").click(function (){
			let tmpEmailCode = $("#emailCode").val();

			$.ajax({
			url : 'confirmCode.mem',
			type : 'get',
			data : {"tmpEmailCode" : tmpEmailCode},
			dataType : 'json',
			async: false,
			success : function(data) {
				console.log(data);
				if (data.activation == "success"){
					printSuccessMsg("emailCode", "이메일 인증이 완료되었습니다.");
					userEmailValid = true;
				} else if (data.activation == "fail"){
				 // 발송 실패
					printErrMsg("emailCode", "인증번호를 다시 확인해주십시오.", true);
				}
			},
			error : function() {
			},
			complete : function() {
			}
		});
	});

}); // End of Doc


	// 함수 시작!!!


	// 회원 가입을 눌렀을 때 유효성 검사 함수
function validCheck() {
	let isValid = false;
	
	let userIdValid = validUserId();
	let userPwdValid = matchingPwd();
	let checkAgree = $("input[type=checkbox][name=agree]:checked").val();
	
	if (checkAgree == undefined) {
		printErrMsg("agree", "가입조항을 체크해주세요", true);
	}

	console.log(userIdValid, userPwdValid, userEmailValid, checkAgree)
	if (userIdValid && userPwdValid && userEmailValid && checkAgree == "Y"){
		isValid = true;
	}
	
	return isValid;
	
}

// 아이디 유효성 검사 함수
function validUserId () {
	// 아이디 유효성 검사
	// 3자 이상 8자 이하
	let isValid = false;
	let tmpUserId = $("#userId").val();
// 		alert("1");
	if (tmpUserId.length > 2 && tmpUserId.length < 9){
		// 아이디 중복 검사
		$.ajax({
			url : 'duplicateUserId.mem',
			type : 'get',
			data : {
					"tmpUserId" : tmpUserId
				   },
			dataType : 'json',
			async: false,
			success : function(data) {
				console.log(data);
				if (data.isDuplicate == "true"){
				 // 아이디 중복
				 printErrMsg("userId", "이미 사용된 아이디입니다.", true);
				} else if (data.isDuplicate == "false"){
				 // 사용 가능한 아이디
				 printSuccessMsg("userId", "사용 가능한 아이디입니다.");
				 isValid = true;
				}
			},
			error : function() {
			},
			complete : function() {
			}
		});
	} else {
		printErrMsg("userId", "아이디는 3자 이상 8자 이하로 필수입니다.", true);
	}
	return isValid;
}

function validUserPwd() {
	let regPwd = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
	let userPwd = $("#userPwd").val();
	if (!regPwd.test(userPwd) || userPwd.length < 8) {
		$("#userPwd").val("");	
   		printErrMsg("userPwd", "비밀번호는 8자리 이상, 최소 하나 이상의 대문자 및 특수기호를 포함해야 합니다.", true);
	}
}

function matchingPwd(){
	let isValid = false;	
	let userPwd = $("#userPwd").val();
	let userPwd2 = $("#userPwd2").val();
	if (userPwd != userPwd2) {
		printErrMsg("userPwd2", "비밀번호가 일치하지 않습니다.", true);
		$("#userPwd2").val("");	
	} else if (userPwd == userPwd2) {
		printSuccessMsg("userPwd2", "비밀번호가 일치합니다.");
		isValid = true;
	}
	return isValid;
}


function validEmail(){
	let inputEmail = $("#userEmail").val();
	let regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i
	if (!regEmail.test(inputEmail)){
		printErrMsg("userEmail", "이메일 양식에 맞지 안습니다. 확인 후 기입해 주세요", true);
		$("#userEmail").val("");
	}
	return true;
}


// 유효성 검사 Error 메시지
function printErrMsg (id, msg, isFocus) {
	let errMsg = "<div class='errMsg'>" + msg + "</div>";
	$(errMsg).insertAfter($("#" + id));
	if (isFocus) {
		$("#" + id).focus();
	}			
	$(".errMsg").hide(2000);
}

function printSuccessMsg (id, msg) {
	let successMsg = "<div class='successMsg'>" + msg + "</div>";
	$(successMsg).insertAfter($("#" + id));	
	$(".successMsg").hide(2000);
}

	
	