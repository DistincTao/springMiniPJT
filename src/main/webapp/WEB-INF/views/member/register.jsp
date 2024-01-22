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

<title>Register Page</title>
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>

<div class="container">
	<h1>회원 가입</h1>
	<form action="registerMember.mem" method="post" enctype="multipart/form-data">
 		<div class="mb-3 mt-3">
    		<label for="userId" class="form-label">USER ID:</label>
    		<input type="text" class="form-control" id="userId" placeholder="Enter ID" name="userId">
  		</div>
  		<div class="mb-3 mt-3">
    		<label for="uesrPwd" class="form-label">PASSWORD:</label>
   			<input type="password" class="form-control" id="userPwd" placeholder="Enter password" name="userPwd">
 		</div>
  		<div class="mb-3 mt-3">
    		<label for="uesrPwd2" class="form-label">PASSWORD CONFIRM:</label>
   			<input type="password" class="form-control" id="userPwd2" placeholder="Enter password">
 		</div>
  		<div class="mb-3 mt-3">
    		<label for="userEmail" class="form-label">EMAIL:</label>
   			<input type="text" class="form-control" id="userEmail" placeholder="example@example.com" name="userEmail">
  			<button type="button" class="btn btn-warning" id="sendEmailBtn">Email Validate</button>
  			<div class="codeDiv" style="display : none;">
  				<input type="text" class="form-control" id="emailCode" placeholder="Enter Valification Code" name="userImg">
  				<button type="button" class="btn btn-warning confirmCode"> Check Code </button>
  			</div>
 		</div>
  		<div class="mb-3 mt-3">
    		<label for="userImg" class="form-label">IMAGE:</label>
   			<input type="file" class="form-control" id="userImg" name="userImg">
 		</div>
 		
<!--  		약관 추가  -->
 		
  		<div class="form-check mb-3 mt-3" id="agree">
    		<label class="form-check-label">
      		<input class="form-check-input" type="checkbox" name="agree" value="Y"> 가입 조항에 동의 합니다.
    		</label>
  		</div>
  		<div>  		
  			<button type="submit" class="btn btn-primary" id="signInBtn" onclick="return validCheck();">Submit</button>
  			<button type="reset" class="btn btn-danger" id="signResetBtn">Cancel</button>
  		</div>
	</form>

</div>

<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>