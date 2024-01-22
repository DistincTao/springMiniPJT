<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<c:set var = "contextPath" value="<%=request.getContextPath() %>"></c:set>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script type="text/javascript" src="../js/register.js"></script>
<link rel="stylesheet" href="../css/header.css">
<link rel="stylesheet" href="../css/register.css">
<title>게시판 글쓰기</title>
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>
<!-- 로그인 하지 않은 유저는 login.jsp로 돌려보내기 -->
<c:if test="${sessionScope.login == null }">
	<c:redirect url="../member/login.jsp"></c:redirect>
</c:if>	

<div class="container">
	<h1>게시판 답글 쓰기</h1>
	<form action="replyBoard.bo" method="post">
 		<div class="mb-3 mt-3">
    		<label for="writer" class="form-label">WRITER:</label>
    		<input type="text" class="form-control" id="writer" name="writer" value="${sessionScope.login.userId }" readonly>
  		</div>
  		<div class="mb-3 mt-3">
    		<label for="title" class="form-label">TITLE:</label>
   			<input type="text" class="form-control" id="title" name="title">
 		</div>
  		<div class="mb-3 mt-3">
    		<label for="content" class="form-label">CONTENTS:</label>
   			<textarea rows="20" style="width : 100%" id="content" name="content"></textarea>
 		</div>
  		<div> 
  			<input type="hidden" name="ref" value="${param.ref }"> 		
  			<input type="hidden" name="step" value="${param.step }"> 		
  			<input type="hidden" name="refOrder" value="${param.refOrder }">
  			
  			<button type="submit" class="btn btn-primary">Submit</button>
  			<button type="reset" class="btn btn-danger" onclick="location.href='listAll.bo'">Cancel</button>
  		</div>
	</form>

</div>

<jsp:include page="../footer.jsp"></jsp:include>

</body>
</html>