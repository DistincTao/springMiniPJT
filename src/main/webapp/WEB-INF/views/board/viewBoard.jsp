<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"></c:set>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://kit.fontawesome.com/acb94ab00c.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="/resources/css/header.css?after">
<link rel="stylesheet" href="/resources/css/viewBoard.css">
<script type="text/javascript" src="/resources/js/viewBoard.js"></script>
<!-- <script type="text/javascript" src="/resources/js/reply.js"></script> -->
<script type="text/javascript">
$(function(){
// 	getAllReplies();
	getAllReplies(1);
	
	let boardNo = '${board.boardNo}'
	let pageNo = '${pageInfo.pageNo}'
	let status = '${param.status}';
	if (status == 'noPermission') {
		alert("수정권한이 없습니다.");
	}
	
	$(".modalClose").click(function(){
		$("#updateReplydModal").hide();
		$("#deleteReplydModal").hide();

	})
// 	alert('${sessionScope.login.userId}');
	$("#replyTextWithoutLogin").click (function (){
		alert("login 후 사용 해주세요");
		location.href = "/member/login?redirectUrl=viewBoard&boardNo=" + boardNo;
	})
	// 좋아요
	$(".fa-heart").click (function(){
		if ($(this).attr("id") == "dislike") {
			$(this).removeClass("fa-regular").addClass("fa-solid");
			$(this).attr("id", "like");
		} else if ($(this).attr("id") == "like") {
			$(this).removeClass("fa-solid").addClass("fa-regular");
			$(this).attr("id", "dislike");
		}	
// 		console.log($(this).attr("id"));
		sendBehavior($(this).attr("id"));
	})

}); // onload 종료 시점

function sendBehavior(behavior){
	
	let boardNo = '${board.boardNo}';
	let userId = '${sessionScope.login.userId}';
	console.log(boardNo,   userId);
	if (userId == ""){
		location.href="/member/login?redirectUrl=viewBoard&boardNo=" + boardNo;
	} else {
		
		$.ajax({
			url : "/board/likedislike",
			type : "POST", // 통신방식 (GET, POST, PUT, DELETE)
			data : {
				"boardNo" : boardNo,
				"userId" : userId,
				"behavior" : behavior
			},
			dataType : "text", // MIME Type
			success : function (data){
				console.log(data);
				if (data == "success") {
					console.log("성공");
				}
			},
			error : function (){
				alert("Error")
			},
			complete : function (){},
		});
	}
}

// function getLikeCnt(boardNo) {
// 	$.ajax({
// 		url : "/board/likeCount",
// 		type : "POST", // 통신방식 (GET, POST, PUT, DELETE)
// 		data : boardNo,
// 		dataType : "text", // MIME Type
// 		success : function (data){
// 				console.log(data);

// 		},
// 		error : function (){
// 			alert("Error")
// 		},
// 		complete : function (){},
// 	});
// }

let replyData;
let replyNo;
// function getAllReplies() {
// 	let boardNo = ${board.boardNo};
// 	let pageNo = 1;
// 	if (nextPage != 1) {
// 		pageNo = nextPage;
// 	}
	
// 	$.ajax({
// 		url : "/reply/all/" + boardNo + "/" + pageNo,
// 		type : "GET", // 통신방식 (GET, POST, PUT, DELETE)
// 		dataType : "json", // MIME Type
// 		success : function (data){
// 			console.log(data);
// 			displayAllReplies(data);
// 			replyData = data;
// 		},
// 		error : function (){},
// 		complete : function (){},
// 	});
// }

function getAllReplies(nextPage) {
	let boardNo = ${board.boardNo};
	let pageNo = 1;
	if (nextPage != 1) {
		pageNo = nextPage;
	}
	
	$.ajax({
		url : "/reply/all/" + boardNo + "/" + pageNo,
		type : "GET", // 통신방식 (GET, POST, PUT, DELETE)
		dataType : "json", // MIME Type
		success : function (data){
			console.log(data);
			displayAllReplies(data, pageNo);
			replyData = data;
		},
		error : function (){},
		complete : function (){},
	});
}

// function displayAllReplies(json) {
// 	let output = "<ul class='list-group'>";
// 	if (json.replyList.length > 0) {
// 		$.each(json.replyList, function(i, elem){
// 			if (elem.isDelete == "N") {
// 			  output += "<li class='list-group-item'>";
// 			  output += `<div class='replyText'>\${elem.replyText}</div>`;
// 			  output += `<div class='replyInfo'><span class="replier">\${elem.replier}</span>`;	  
// 			  let elapsedTime = procPostDate(elem.postDate)
// 			  output += `<span class="postDate">\${elapsedTime}</span></div>`;
			  
// // 		  output += `<span>\${elem.postDate}</span></div>`;

// 		      output += `<div class="replyBtns"><img src="/resources/img/modify.png" onclick="updateModal('\${elem.replyNo}', '\${elem.replyText}')"/>`;
// 			  output += `<img src="/resources/img/delete.png" onclick="deleteModal(\${elem.replyNo})"/></div></li>`;
			  
// 			} else if (elem.isDelete == "Y") {
// 				  output += "<li class='list-group-item'>";
// 				  output += `<div class='replyText'>삭제된 댓글입니다.</div></li>`;
// 			}
// 		});
// 	}
// 	output += "</ul>"
	
// 	$(".allReplies").html(output);
// }

function displayAllReplies(json, pageNo) {
	let output = "<ul class='list-group'>";
	if (json.replyList.length > 0) {
		$.each(json.replyList, function(i, elem){
			if (elem.isDelete == "N") {
			  output += "<li class='list-group-item'>";
			  output += `<div class='replyText'>\${elem.replyText}</div>`;
			  output += `<div class='replyInfo'><span class="replier">\${elem.replier}</span>`;	  
			  let elapsedTime = procPostDate(elem.postDate);
			  output += `<span class="postDate">\${elapsedTime}</span></div>`;
			  
// 		  output += `<span>\${elem.postDate}</span></div>`;
				if ('${sessionScope.login.userId}' == elem.replier) {
			      output += `<div class="replyBtns"><img src="/resources/img/modify.png" onclick="updateModal('\${elem.replyNo}', '\${elem.replyText}')"/>`;
				  output += `<img src="/resources/img/delete.png" onclick="deleteModal(\${elem.replyNo})"/></div></li>`;		  
				}
			} else if (elem.isDelete == "Y") {
			  output += "<li class='list-group-item'>";
			  output += `<div class='replyText'>삭제된 댓글입니다.</div></li>`;
			}
		})
	}
	output += "</ul>"
	
	let pageInfo = json.pageInfo;
	let pagingOutput = `<ul class="pagination">`;
	if (pageInfo.pageNo == 1 || pageInfo.pageNo == null) {
		pagingOutput += `<li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>`;
	} else {
		pagingOutput += `<li class="page-item"><a class="page-link"
			onclick="getAllReplies(\${pageInfo.pageNo} - 1);">Previous</a></li>`;
	}
	console.log(pageInfo);
	for (let paging = pageInfo.startPageNum; paging <= pageInfo.endPageNum; paging++){
		if (pageInfo.pageNo == paging) {
			pagingOutput += `<li class="page-item active"><a class="page-link"
				onclick="getAllReplies(\${paging});">\${paging}</a></li>`;
		} else if (pageInfo.pageNo == null && pageInfo.pageNo == 1) {
			pagingOutput += `<li class="page-item active"><a class="page-link"
				onclick="getAllReplies(\${paging});">\${paging}</a></li>`;
		} else {
			pagingOutput += `<li class="page-item"><a class="page-link"
				onclick="getAllReplies(\${paging});">\${paging}</a></li>`;
		}
	}
	
	if (pageNo < pageInfo.totalPageCnt) {
		pagingOutput += `<li class="page-item "><a class="page-link"
			onclick="getAllReplies(\${pageNo} + 1);">Next</a></li>`;
	} else if (pageNo == null && pageInfo.pageNo < pageInfo.totalPageCnt){
		pagingOutput += `<li class="page-item "><a class="page-link"
			onclick="getAllReplies(2);">Next</a></li>`;
	} else {
		pagingOutput += `<li class="page-item disabled"><a class="page-link" href="#">Next</a></li>`;
	}
	
	pagingOutput += `</ul>`;
	
	$(".allReplies").html(output);
	$(".replyPagenation").html(pagingOutput);
}

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
			return gapTime + value.name + " ago";
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
				getAllReplies(1);
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
				getAllReplies(1);
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
</script>

</head>
<body>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<div class="mycontainer">	
<%-- 	${board } --%>
<%-- 	${fileList } --%>
<%-- 	${sessionScope.login.userId } --%>
	<h1>게시판 글 조회</h1>
	<div class="container-fluid about py-7">
            <div class="container py-5">
                <div class="row g-5 align-items-center">
                    	<c:if test="${fileList != null}">
                    	<div class="col-lg-5 userImg">
                    		<c:forEach var="file" items="${fileList }">
<%--                     		${file.thumbFilename != null} --%>
                    			<c:choose>
                    			<c:when test="${file.thumbFilename != null}">
 			                   		<img src="\resources\uploads${file.newFilename }" class="img-fluid rounded" alt=""/>	
                    			</c:when>
                    			<c:otherwise>
                    				<a href='\resources\uploads${file.newFilename}'>${file.originalFilename}</a>
                    			</c:otherwise>
                    			</c:choose>
                    		</c:forEach>
                    	</div>
						</c:if>
                    <div class="col-lg-7">
                        <div>
                            <h1 class="display-6 mb-4">${board.title }</h1>
                            <p class="fs-7 text-uppercase boardNo">글번호 : <span id="boardNo">${board.boardNo }</span></p>
                            <p class="fs-7 text-uppercase text-primary">작성자 : ${board.writer }</p>
                            <p class="fs-7 text-uppercase">작성일 : ${board.postDate }</p>
                            <div class="counts">
	                            <div class="fs-7 text-uppercase">
	                            	<img src="/resources/img/book.png" style="width : 24px; height : 24px;"><span class="badge bg-primary">${board.readCount }</span>
                            	</div>
	                            <div class="fs-7 text-uppercase likeCnt" >
									<c:set var="hasHeart" value="false"></c:set>
									<c:forEach var="user" items="${likeUsers }">
									<c:choose>
										<c:when test="${sessionScope.login.userId == user }">
									  		<c:set var="hasHeart" value="true"></c:set>
											<i class="fa-solid fa-heart" style="width : 24px; height : 24px; color : red;" id="like"></i>											
										
										</c:when>
									</c:choose>
									</c:forEach>
										<c:if test="${hasHeart == false }">
											<i class="fa-regular fa-heart" style="width : 24px; height : 24px; color : red;" id="dislike"></i>																				
										</c:if>

	                            	<span class="badge bg-primary currLike">${board.likeCount }</span>
	             					
	             					<c:if test="${likeUsers.size() le 3 }">
										<div>이 글을<span>
	                            	<c:forEach var="user" items="${likeUsers }">
	                            		${user }님
	                            	</c:forEach>
										</span>이 좋아합니다.</div>
	             					</c:if>
	             					<c:if test="${likeUsers.size() ge 4 }">
	             						<div>이 글을<span>
             						<c:forEach var="user" items="${likeUsers }" begin="1" end="3">
             							${user }님
             						</c:forEach>
             							</span>외 ${likeUsers.size() - 3 } 명이 좋아합니다.</div>
	             					</c:if>
                            	</div>
                            </div>
                            <div class="mb-3 fs-6">${board.content }</div>
                        </div>
<%--                         <c:choose> --%>
<%--                         	<c:when test="${sessionScope.login != null && (sessionScope.login.userId == board.writer || sessionScope.login.isAdmin == 'Y') }"> --%>
		                        <button type="button" class="btn btn-primary rounded-pill px-5" onclick="updateBoard();">Update</button>
		                        <button type="button" class="btn btn-danger rounded-pill px-5" onclick="deleteBoardModal();">Delete</button>                        	
 		                        <button type="button" class="btn btn-warning rounded-pill px-5" onclick="location.href='replyBoard?ref=${board.ref }&step=${board.step }&refOrder=${board.refOrder }'">Reply</button>
<%-- 		                        <button type="button" class="btn btn-primary rounded-pill px-5" onclick="location.href ='updateBoard?boardNo=${board.boardNo}&writer=${board.writer }'">Update</button> --%>
<%-- 		                        <button type="button" class="btn btn-danger rounded-pill px-5" onclick="location.href ='deleteBoard?boardNo=${board.boardNo}&writer=${board.writer }'">Delete</button>                        	 --%>
<%--  		                        <button type="button" class="btn btn-warning rounded-pill px-5" onclick="location.href='replyBoard?ref=${board.ref }&step=${board.step }&refOrder=${board.refOrder }'">Reply</button> --%>
<%--                         	</c:when> --%>
<%--                         	<c:when test="${sessionScope.login != null && sessionScope.login.userId != board.writer }"> --%>
<!-- 		                        <button type="button" class="btn btn-primary rounded-pill px-5" disabled>Update</button> -->
<!-- 		                        <button type="button" class="btn btn-danger rounded-pill px-5" disabled >Delete</button>                        	 -->
<%--  		                        <button type="button" class="btn btn-warning rounded-pill px-5" onclick="location.href='replyBoard?ref=${board.ref }&step=${board.step }&refOrder=${board.refOrder }'">Reply</button> --%>
<%--                         	</c:when> --%>
<%--                         	<c:otherwise> --%>
<!-- 		                        <button type="button" class="btn btn-primary rounded-pill px-5" disabled>Update</button> -->
<!-- 		                        <button type="button" class="btn btn-danger rounded-pill px-5" disabled >Delete</button>                        	 -->
<!--  		                        <button type="button" class="btn btn-warning rounded-pill px-5" disabled>Reply</button> -->
<%--                         	</c:otherwise> --%>
<%--                         </c:choose> --%>
                        <a href="listAll" class="btn btn-success rounded-pill px-5">List</a>
                    </div> 
                </div>
        <!-- 댓글 -->
        <c:choose>
        <c:when test="${sessionScope.login != null}">
        <div class="replyInputDiv">
            <div class="mb-3 mt-3">
		    	<label for="replyText">Comments:</label>
	    		<textarea class="form-control" rows="2" style="width : 100%;" id="replyText"></textarea>
		    </div>
        </div>
        <div>
        	<button type="button" class="btn btn-primary rounded-pill px-5" onclick="saveReply();">Submit</button>
            <button type="button" class="btn btn-danger rounded-pill px-5">Cancel</button>                        	
     	</div>
        </c:when>
        <c:otherwise>
        <div class="replyInputDiv">
            <div class="mb-3 mt-3">
		    	<label for="replyText">Comments:</label>
	    		<textarea class="form-control" rows="5" style="width : 100%;" id="replyTextWithoutLogin" ></textarea>
		    </div>
        </div>
        <div>
        	<button type="button" class="btn btn-primary rounded-pill px-5" disabled>Submit</button>
            <button type="button" class="btn btn-danger rounded-pill px-5" disabled>Cancel</button>                        	
     	</div>
        </c:otherwise>
        </c:choose>
        <div class="allReplies mb-3 mt-3"></div>
        <div class="replyPagenation mb-3 mt-3"></div>
        
        
        
        
            </div>
        </div>
        
	</div>
	
<div class="modal" id="updateBoardModal">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">게시판 수정</h4>
        <button type="button" class="btn-close modalClose" data-bs-dismiss="modal"></button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">
<!-- 	<form id="deleteForm" action="/board/updateBoard" method="POST"> -->
 		<input type="hidden" name="existFile" value="${requestScope.file.newFilename }">
 		
 		<div class="mb-3 mt-3">
    		<label for="boardNo" class="form-label">NUMBER</label>
    		<input type="text" class="form-control" id="boardNo" name="boardNo" value="${board.boardNo }" readonly>
  		</div>
 		<div class="mb-3 mt-3">
    		<label for="writer" class="form-label">WRITER:</label>
    		<input type="text" class="form-control" id="writer" name="writer" value="${board.writer }" readonly>
  		</div>
  		<div class="mb-3 mt-3">
    		<label for="title" class="form-label">TITLE:</label>
   			<input type="text" class="form-control" id="title" name="title" value="${board.title }">
 		</div>
  		<div class="mb-3 mt-3">
    		<label for="content" class="form-label">CONTENTS:</label>
   			<textarea rows="20" style="width : 100%" id="content" name="content">${board.content }</textarea>
 		</div>
		<div>
			<input type="checkbox" name="fileDelete" id="fileDelete" value='delete'> 파일 삭제 
		</div>
  		<div class="mb-3 mt-3" id="changeFile">
    		<label for="upFile" class="form-label">FILE</label>
   			<input type="file" class="form-control" id="upFile" name="upFile">
 		</div> 		
  		<div>  		
  			<button type="submit" class="btn btn-primary" onclick="location.href ='/board/updateBoard?boardNo=${board.boardNo}&writer=${board.writer }'">Submit</button>
  			<button type="reset" class="btn btn-danger" onclick="location.href ='/board/deleteBoard?boardNo=${board.boardNo}&writer=${board.writer }'">Cancel</button>
  		</div>
<!-- 	</form> -->
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger modalClose" data-bs-dismiss="modal">Close</button>
      </div>

    </div>
  </div>
</div>

<div class="modal" id="deleteBoardModal">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">게시판 삭제</h4>
        <button type="button" class="btn-close modalClose" data-bs-dismiss="modal"></button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">
			${board.boardNo} 번 글을 삭제하시겠습니까?
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
  			<button type="submit" class="btn btn-primary" onclick="location.href='/board/deleteBoard?boardNo=${board.boardNo}&writer=${board.writer }'">Delete</button>
  			<button type="reset" class="btn btn-danger modalClose">Cancel</button>      
  	  </div>
    </div>
  </div>
</div>

<div class="modal" id="updateReplydModal">
  <div class="modal-dialog">
    <div class="modal-content">
       <!-- Modal body -->
      <div class="modal-body">
	    <div class="mb-3 mt-3">
		   	<label for="updateReplyText">Comments:</label>
	    	<textarea class="form-control" rows="2" style="width : 100%;" id="updateReplyText"></textarea>
		</div>
	  	<div>  		
	  		<button type="submit" class="btn btn-primary updateBtn" onclick="updateReply(this.id);">Submit</button>
	  		<button type="reset" class="btn btn-danger">Cancel</button>
	  	</div>
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger modalClose" data-bs-dismiss="modal">Close</button>
      </div>

    </div>
  </div>
</div>
<div class="modal" id="deleteReplydModal">
  <div class="modal-dialog">
    <div class="modal-content">
       <!-- Modal body -->
      <div class="modal-body">
	    <div class="mb-3 mt-3 deleteReplyNo"></div>
	  	<div>  		
	  		<button type="submit" class="btn btn-primary deleteBtn" onclick="updateReply(this.id);">Submit</button>
	  	</div>
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger modalClose" data-bs-dismiss="modal">Close</button>
      </div>

    </div>
  </div>
</div>
	
	<jsp:include page="../footer.jsp"></jsp:include>

</body>
</html>