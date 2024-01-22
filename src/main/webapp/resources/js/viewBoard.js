$(function() {
	
	$(".modalClose").click(function(){
		$("#updateBoardModal").hide();
		$("#deleteBoardModal").hide();

	})
	
	
	$("#fileDelete").change(function() {
		if ($("input[type=checkbox][name=fileDelete]:checked").val() == 'delete'){
			$("#changeFile").hide()
		} else {
			$("#changeFile").show()
		}
	
	
		
	})
	
	$("#likeCnt").click(function() {
		let boardNo = $("#boardNo").html();
		if ($("#likeCnt").hasClass("fa-solid") == false)	{
			 $.ajax({
				url : 'likeCount.bo',
				type : 'post',
				data : {"boardNo" : boardNo},
				dataType : 'json',
				async: false,
				success : function(data) {
					console.log(data)
					if (data.status == 'success'){
						$(".currLike").html(data.likeCount);
						$("#likeCnt").toggleClass('fa-solid');
					} else {
						alert("LOGIN 후 사용 가능합니다!");
						location.href="../member/login.jsp";
					}
				},
				error : function() {

				},
				complete : function() {
				}
			});
		} else if ($("#likeCnt").hasClass("fa-solid") == true) {
			 $.ajax({	
				url : 'deleteLikeCount.bo',
				type : 'post',
				data : {"boardNo" : boardNo},
				dataType : 'json',
				async: false,
				success : function(data) {
					console.log(data)
					if (data.status == 'success'){
						$(".currLike").html(data.likeCount);
						$("#likeCnt").toggleClass('fa-solid');
					} 
					
				},
				error : function() {
					alert("LOGIN 후 사용 가능합니다!");
					location.href="../member/login.jsp";
				},
				complete : function() {
				}
			});
		}
	});
	

	
	
})


function updateBoard() {
//	alert("test");
	// 모달 띄우기
	$("#updateBoardModal").show();
	
}

function deleteBoard() {
//	alert("test");
	// 모달 띄우기
	$("#deleteBoardModal").show();
	
}


function deleteBoard () {
	let boardNo = '${requestScope.board.boardNo}'; // js에서 EL 사용 시 ''로 감싸 주어야 함
	$.ajax({
		url : "",
		type : "get",
		data : {"boardNo" : boardNo},
		datatype : "json",
		success : function (data) {
			console.log(data);
		},
		error : function () {},
		complete : function () {}
	});
}


//// 쿠키 북마크 내용 출력
//function bookMarkList() {
//	let cookies = document.cookie;
//	let cookieArr = cookies.split('; ');
//	console.log(cookieArr);
//	$.each(cookieArr, function (index, cookie) {
//		let title = cookieArr[index].split('=')[0];
//		let value = cookieArr[index].split('=')[1];
//		console.log(title, value, cookie);
//		if (value == 'BookMark') {
//			$('#likeList').append(`<div>${title}</div>`);
//		}
//	});
//	// $('.likeList').css({ display: 'block' });
//}

//// 쿠키 관련 함수
//function saveCookie(name, val, expDate) {
//	let now = new Date();
//	now.setDate(now.getDate() + Number(expDate));
//	let newCookie = name + '=' + val + ';expires=' + now.toUTCString();
//	document.cookie = newCookie; // 쿠키 저장
//}
//
//// 해당 cookie를 삭제하는 함수
//function delCookie(name, val) {
//	// 쿠키 삭제
//	let now = new Date();
//	let cookie = name + '=' + val + ';expires=' + now.toUTCString();
//	document.cookie = cookie;
//}
////쿠키를 읽어와 해당 내용이 있는지 없는지를 확인하는 함수
//let cookName = [];
//function getCookie(name) {
//	if (document.cookie != '') {
//		let cookArr = document.cookie.split('; ');
//		// console.log(cookArr);
//		// console.log(document.cookie);
//		$.each(cookArr, function (index, cookie) {
//			cookName[index] = `${cookie.replace(`=BookMark`, '')}`;
//		});
//		for (let i in cookName) {
//			if (cookName[i] == name) {
//				return true;
//			}
//		}
//	}
//}
