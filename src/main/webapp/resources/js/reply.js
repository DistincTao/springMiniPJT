$(function(){
 	getAllReplies();
//	getAllReplies(1);
	
	$(".modalClose").click(function(){
		$("#updateReplydModal").hide();
		$("#deleteReplydModal").hide();

	})
}); // onload 종료 시점

let replyData;
let replyNo;
//function getAllReplies() {
// 	let boardNo = ${board.boardNo};
// 	let pageNo = 1;
// 	if (nextPage != 1) {
// 		pageNo = nextPage;
// 	}
//	
// 	$.ajax({
// 		url : "/reply/all/" + boardNo + "/" + pageNo,
// 		type : "GET", // 통신방식 (GET, POST, PUT, DELETE)
// 		dataType : "json", // MIME Type
// 		success : function (data){
// 			console.log(data);
// 			displayAllReplies(data);
// 			replyData = data;
// 		},
//		error : function (){},
// 		complete : function (){},
// 	});
//}

// function getAllReplies(nextPage) {
//	let boardNum = ${board.boardNo};
//	let boardNo = boardNum;
//	let pageNo = 1;
//	if (nextPage != 1) {
//		pageNo = nextPage;
//	};
//	
//	$.ajax({
//		url : "/reply/all/" + boardNo + "/" + pageNo,
//		type : "GET", // 통신방식 (GET, POST, PUT, DELETE)
//		dataType : "json", // MIME Type
//		success : function (data){
//			console.log(data);
//			displayAllReplies(data);
//			replyData = data;
//		},
//		error : function (){},
//		complete : function (){},
//	});
// }


 function displayAllReplies(json) {
	let output = "<ul class='list-group'>";
 	if (json.replyList.length > 0) {
 		$.each(json.replyList, function(i, elem){
			if (elem.isDelete == "N") {
 			  output += "<li class='list-group-item'>";
 			  output += `<div class='replyText'>\${elem.replyText}</div>`;
 			  output += `<div class='replyInfo'><span class="replier">\${elem.replier}</span>`;	  
 			  let elapsedTime = procPostDate(elem.postDate)
 			  output += `<span class="postDate">\${elapsedTime}</span></div>`;
			  
 // 		  output += `<span>\${elem.postDate}</span></div>`;
 		      output += `<div class="replyBtns"><img src="/resources/img/modify.png" onclick="updateModal('\${elem.replyNo}', '\${elem.replyText}')"/>`;
 			  output += `<img src="/resources/img/delete.png" onclick="deleteModal(\${elem.replyNo})"/></div></li>`;
	  
 			} else if (elem.isDelete == "Y") {
 				  output += "<li class='list-group-item'>";
 				  output += `<div class='replyText'>삭제된 댓글입니다.</div></li>`;
 			}
 		});
 	}
 	output += "</ul>"
	
 	$(".allReplies").html(output);
 }

//function displayAllReplies(json, pageNo) {
//	let output = "<ul class='list-group'>";
//	if (json.replyList.length > 0) {
//		$.each(json.replyList, function(i, elem){
//			if (elem.isDelete == "N") {
//			  output += "<li class='list-group-item'>";
//			  output += `<div class='replyText'>\${elem.replyText}</div>`;
//			  output += `<div class='replyInfo'><span class="replier">\${elem.replier}</span>`;	  
//			  let elapsedTime = procPostDate(elem.postDate)
//			  output += `<span class="postDate">\${elapsedTime}</span></div>`;
//			  
//// 		  output += `<span>\${elem.postDate}</span></div>`;
//
//		      output += `<div class="replyBtns"><img src="/resources/img/modify.png" onclick="updateModal('\${elem.replyNo}', '\${elem.replyText}')"/>`;
//			  output += `<img src="/resources/img/delete.png" onclick="deleteModal(\${elem.replyNo})"/></div></li>`;
//			  
//			} else if (elem.isDelete == "Y") {
//				  output += "<li class='list-group-item'>";
//				  output += `<div class='replyText'>삭제된 댓글입니다.</div></li>`;
//			}
//		});
//	}
//	output += "</ul>"
//	
//	let pageInfo = json.pageInfo;
//	let pagingOutput = `<ul class="pagination">`;
//	if (pageInfo.pageNo == 1 || pageInfo.pageNo == null) {
//		pagingOutput += `<li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>`;
//	} else {
//		pagingOutput += `<li class="page-item"><a class="page-link"
//			onclick="getAllReplies(\${pageInfo.pageNo} - 1);">Previous</a></li>`;
//	}
//	console.log(pageInfo);
//	for (let paging = pageInfo.startPageNum; paging <= pageInfo.endPageNum; paging++){
//		if (pageInfo.pageNo == paging) {
//			pagingOutput += `<li class="page-item active"><a class="page-link"
//				onclick="getAllReplies(\${paging});">\${paging}</a></li>`;
//		} else if (pageInfo.pageNo == null && pageInfo.pageNo == 1) {
//			pagingOutput += `<li class="page-item active"><a class="page-link"
//				onclick="getAllReplies(\${paging});">\${paging}</a></li>`;
//		} else {
//			pagingOutput += `<li class="page-item"><a class="page-link"
//				onclick="getAllReplies(\${paging});">\${paging}</a></li>`;
//		}
//	}
//	
//	if (pageNo < pageInfo.totalPageCnt) {
//		pagingOutput += `<li class="page-item "><a class="page-link"
//			onclick="getAllReplies(\${pageNo} + 1);">Next</a></li>`;
//	} else if (pageNo == null && pageInfo.pageNo < pageInfo.totalPageCnt){
//		pagingOutput += `<li class="page-item "><a class="page-link"
//			onclick="getAllReplies(2);">Next</a></li>`;
//	} else {
//		pagingOutput += `<li class="page-item disabled"><a class="page-link" href="#">Next</a></li>`;
//	}
//	
//	pagingOutput += `</ul>`;
//	
//	$(".allReplies").html(output);
//	$(".replyPagenation").html(pagingOutput);
//}



function procPostDate(date){
	let postDate = new Date(date); // 댓글 작성 시간
	let now = new Date();
	let timeDiff = (now - postDate) / 1000; // 초단위 변환
// 	let timeDiff = 3000
	
	let times =[
		{name : " day", time : 24 * 60 * 60},
		{name : " hours", time : 60 * 60},
		{name : " mins", time : 60},
	]
	
	for (let value of times) {
		let gapTime = Math.floor(timeDiff / value.time);
// 		console.log(value.time);
// 		console.log(timeDiff);
// 		console.log(gapTime);
	
		if (gapTime > 0) {
			if (timeDiff > 24 * 60 * 60) {
				return postDate.toLocaleString();
			}
			
			return gapTime + value.name + " ago"
		}
	}
	
	return "just before";
}

function saveReply(){
	let boardNo = '${board.boardNo}';
	let replyText = $("#replyText").val();
	let replier = '${sessionScope.login.userId}';
	
	console.log(boardNo, replyText, replier);
	
	let newReply = {
		"boardNo" : boardNo,
		"replyText" : replyText,
		"replier" : replier
	}
	console.log(JSON.stringify(newReply));
	
	$.ajax({
		url : "/reply/",
		type : "POST", // 통신방식 (GET, POST, PUT, DELETE)
		data : JSON.stringify(newReply),
		headers : {
			// 송신하는 데이터dml MIME type 전달
			"Content-Type" : "application/json",
			
			// PUT, DELETE, PATCH 등의 REST에서 사용되는 http-method가 동작하지 않는
			// 과거의 웹브라우저에서 POST 방식으로 동작하도록 한다.
			"X-HTTP-Method-Override" : "POST"
		},
		dataType : "text", // MIME Type
		success : function (data){
			console.log(data);
			if (data == "success") {
				getAllReplies();
				$("#replyText").val("");				
			}
		},
		error : function (){
			alert ("Error 발생");
		},
		complete : function (){},
	});
}

function updateModal(no, text) {
	
	$("#updateReplydModal").show();
	$("#updateReplyText").html(text);
	$(".updateBtn").attr("id", no);

}
function deleteModal(no) {
	
	$("#deleteReplydModal").show();
	$(".deleteReplyNo").html("해당 댓글을 삭제 하시겠습니까?");
	$(".deleteBtn").attr("id", no);

}

function updateReply(no){
	let replyText = $("#updateReplyText").val();
	console.log($("#updateReplyText").val());
	let replyNo = no;
	
	let updateReply = {
			"replyText" : replyText,
			"replyNo" : replyNo
		}	

	console.log(JSON.stringify(updateReply));
		
	$.ajax({
		url : "/reply/" + replyNo,
		type : "PUT", // 통신방식 (GET, POST, PUT, DELETE)
		data : JSON.stringify(updateReply),
// 		data : {"replyText" : replyText },
		headers : {
			// 송신하는 데이터dml MIME type 전달
			"Content-Type" : "application/json",
			
// 			PUT, DELETE, PATCH 등의 REST에서 사용되는 http-method가 동작하지 않는
// 			과거의 웹브라우저에서 POST 방식으로 동작하도록 한다.
			"X-HTTP-Method-Override" : "PUT"
		},
		dataType : "text", // MIME Type => 회신 받을 타입!!
		success : function (data){
			console.log(data);
			if (data == "success") {
				getAllReplies();
			$("#updateReplydModal").hide();
			$("#deleteReplydModal").hide();
			}

		},
		error : function (){
// 			alert ("Error 발생");
// 			getAllReplies();
		},
		complete : function (){},
	});
}

// function deleteReply(no){
// 	console.log("왜 안되냐;;;");
// 	let replyNo = no;
// 	$.ajax({
// 		url : "/reply/" + no,
// 		type : "POST", // 통신방식 (GET, POST, PUT, DELETE)
// // 		headers : {
// // 			// 송신하는 데이터dml MIME type 전달
// // 			"Content-Type" : "application/json",
			
// // 			// PUT, DELETE, PATCH 등의 REST에서 사용되는 http-method가 동작하지 않는
// // 			// 과거의 웹브라우저에서 POST 방식으로 동작하도록 한다.
// // 			"X-HTTP-Method-Override" : "POST"
// // 		},
// 		dataType : "json", // MIME Type
// 		success : function (data){
// 			console.log(data);
// 			getAllReplies();
// 		},
// 		error : function (){
// 			alert ("Error 발생");
// 		},
// 		complete : function (){},
// 	});
// }
