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
<script type="text/javascript" src="/resources/js/writeBoard.js"></script>
<link rel="stylesheet" href="/resources/css/header.css?afters">

<style type="text/css">
	.upFileArea {
		width : 100%;
		height : 100px;
		border : 1px dotted #333;
		
		font-weight : bold;
		color : gray;
		background-color: #eff9f7;
		
		text-align: center;
		line-height: 100px;
		}
	.removeIcon {
		width : 16px;
	}
</style>
<title>게시판 글쓰기</title>
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>
<!-- 로그인 하지 않은 유저는 login.jsp로 돌려보내기 -->
<%-- <c:if test="${sessionScope.login == null }"> --%>
<%-- 	<c:redirect url="../member/login.jsp"></c:redirect> --%>
<%-- </c:if>	 --%>

<div class="container">
	<h1>게시판 글쓰기</h1>
	<form action="writeBoard.bo" method="post">
 		<div class="mb-3 mt-3">
    		<label for="writer" class="form-label">WRITER:</label>
    		<input type="text" class="form-control" id="writer" name="writer" value="${sessionScope.login.userId }" readonly>
  		</div>
  		<div class="mb-3 mt-3">
    		<label for="title" class="form-label">TITLE:</label>
   			<input type="text" class="form-control" id="title" placeholder="제목을 입력하세요" name="title">
 		</div>
  		<div class="mb-3 mt-3">
    		<label for="content" class="form-label">CONTENTS:</label>
   			<textarea rows="20" style="width : 100%" id="content" name="content"></textarea>
 		</div>

  		<div class="mb-3 mt-3">
    		<label for="upFile" class="form-label">FILE : </label>
   			<div class="upFileArea upLoadFiles">Drag and Drop Files</div>
 		</div>
  		<div>  		
  			<button type="submit" class="btn btn-primary">Submit</button>
  			<button type="reset" class="btn btn-danger" onclick="btnCancel()">Cancel</button>
  		</div>
	</form>

</div>

<jsp:include page="../footer.jsp"></jsp:include>

</body>
</html>