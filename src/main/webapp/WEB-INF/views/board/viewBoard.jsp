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
<link rel="stylesheet" href="../css/header.css?after">
<link rel="stylesheet" href="../css/viewBoard.css?after">
<script type="text/javascript" src="../js/viewBoard.js"></script>
</head>
<body>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<div class="mycontainer">	
<%-- 	${requestScope.board } --%>
<%-- 	${requestScope.file } --%>
<%-- 	${sessionScope.login.userId } --%>
<%-- 	${requestScope.board.writer } --%>
	<h1>게시판 글 조회</h1>
	<div class="container-fluid about py-7">
            <div class="container py-5">
                <div class="row g-5 align-items-center">
                    	<c:if test="${requestScope.file != null}">
                    	<div class="col-lg-5 userImg">
                    		<img src="${contextPath }/${requestScope.file.newFilename }" class="img-fluid rounded" alt="">	
                    	</div>
						</c:if>
                    <div class="col-lg-7">
                        <div>
                            <h1 class="display-6 mb-4">${requestScope.board.title }</h1>
                            <p class="fs-7 text-uppercase boardNo">글번호 : <span id="boardNo">${requestScope.board.boardNo }</span></p>
                            <p class="fs-7 text-uppercase text-primary">작성자 : ${requestScope.board.writer }</p>
                            <p class="fs-7 text-uppercase">작성일 : ${requestScope.board.postDate }</p>
                            <div class="counts">
	                            <div class="fs-7 text-uppercase">
	                            	<img src="../img/book.png" style="width : 24px; height : 24px;"><span class="badge bg-primary">${requestScope.board.readCount }</span>
                            	</div>
	                            <div class="fs-7 text-uppercase likeCnt" >
	                            	<c:choose>
	                            	<c:when test="${sessionScope.login == null}">
	                            		<i class="fa-regular fa-heart" style="width : 24px; height : 24px; color : red;" id="likeCnt"></i>
	                            	</c:when>
	                            	<c:when test="${sessionScope.login != null && requestScope.likeLog.userId != sessionScope.login.userId }">
	                            		<i class="fa-regular fa-heart" style="width : 24px; height : 24px; color : red;" id="likeCnt"></i>
	                            	</c:when>
	                            	<c:when test="${sessionScope.login != null && requestScope.likeLog.userId == sessionScope.login.userId }">	                            	
	                            		<i class="fa-regular fa-solid fa-heart" style="width : 24px; height : 24px; color : red;" id="likeCnt"></i>
	                            	</c:when>
	                            	</c:choose>
	                            	<span class="badge bg-primary currLike">${requestScope.board.likeCount }</span>
                            	</div>
                            </div>
                            <div class="mb-3 fs-6">${requestScope.board.content }</div>
                        </div>
                        <c:choose>
                        	<c:when test="${sessionScope.login != null && (sessionScope.login.userId == requestScope.board.writer || sessionScope.login.isAdmin == 'Y') }">
		                        <button type="button" class="btn btn-primary rounded-pill px-5" onclick="updateBoard()">Update</button>
		                        <button type="button" class="btn btn-danger rounded-pill px-5" onclick="deleteBoard()">Delete</button>                        	
 		                        <button type="button" class="btn btn-warning rounded-pill px-5" onclick="location.href='replyBoard.jsp?ref=${requestScope.board.ref }&step=${requestScope.board.step }&refOrder=${requestScope.board.refOrder }'">Reply</button>
                        	</c:when>
                        	<c:when test="${sessionScope.login != null && sessionScope.login.userId != requestScope.board.writer }">
		                        <button type="button" class="btn btn-primary rounded-pill px-5" disabled>Update</button>
		                        <button type="button" class="btn btn-danger rounded-pill px-5" disabled >Delete</button>                        	
 		                        <button type="button" class="btn btn-warning rounded-pill px-5" onclick="location.href='replyBoard.jsp?ref=${requestScope.board.ref }&step=${requestScope.board.step }&refOrder=${requestScope.board.refOrder }'">Reply</button>
                        	</c:when>
                        	<c:otherwise>
		                        <button type="button" class="btn btn-primary rounded-pill px-5" disabled>Update</button>
		                        <button type="button" class="btn btn-danger rounded-pill px-5" disabled >Delete</button>                        	
 		                        <button type="button" class="btn btn-warning rounded-pill px-5" disabled>Reply</button>
                        	</c:otherwise>
                        </c:choose>
                        <a href="${contextPath }/board/listAll.bo" class="btn btn-success rounded-pill px-5">List</a>
                    </div> 
                </div>
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
	<form id="deleteForm" action="updateBoard.bo" method="post" enctype="multipart/form-data">
 		<input type="hidden" name="existFile" value="${requestScope.file.newFilename }">
 		<div class="mb-3 mt-3">
    		<label for="boardNo" class="form-label">NUMBER</label>
    		<input type="text" class="form-control" id="boardNo" name="boardNo" value="${requestScope.board.boardNo }" readonly>
  		</div>
 		<div class="mb-3 mt-3">
    		<label for="writer" class="form-label">WRITER:</label>
    		<input type="text" class="form-control" id="writer" name="writer" value="${requestScope.board.writer }" readonly>
  		</div>
  		<div class="mb-3 mt-3">
    		<label for="title" class="form-label">TITLE:</label>
   			<input type="text" class="form-control" id="title" name="title" value="${requestScope.board.title }">
 		</div>
  		<div class="mb-3 mt-3">
    		<label for="content" class="form-label">CONTENTS:</label>
   			<textarea rows="20" style="width : 100%" id="content" name="content">${requestScope.board.content }</textarea>
 		</div>
		<div>
			<input type="checkbox" name="fileDelete" id="fileDelete" value='delete'> 파일 삭제 
		</div>
  		<div class="mb-3 mt-3" id="changeFile">
    		<label for="upFile" class="form-label">FILE</label>
   			<input type="file" class="form-control" id="upFile" name="upFile">
 		</div> 		
  		<div>  		
  			<button type="submit" class="btn btn-primary">Submit</button>
  			<button type="reset" class="btn btn-danger">Cancel</button>
  		</div>
	</form>
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
			${requestScope.board.boardNo} 번 글을 삭제하시겠습니까?
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
  			<button type="submit" class="btn btn-primary" onclick="location.href='${contextPath }/board/deleteBoard.bo?boardNo=${requestScope.board.boardNo}'">Delete</button>
  			<button type="reset" class="btn btn-danger modalClose">Cancel</button>      
  	  </div>
    </div>
  </div>
</div>
	
	<jsp:include page="../footer.jsp"></jsp:include>

</body>
</html>