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
<script src="https://kit.fontawesome.com/acb94ab00c.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="/resources/css/header.css?after">
<!-- <script type="text/javascript" src="/resources/js/login.js"></script> -->
<style type="text/css">

</style>
<title>LOGIN</title>
</head>
<body>

<script type="text/javascript">
$(function(){

// 	let loginStatus = '${status}';
	let loginStatus = '${param.status }';
	if (loginStatus == 'fail'){
		alert("아이디 또는 비밀번호를 다시 확인 해주세요");
	}

});
</script>
<jsp:include page="../header.jsp"></jsp:include>

<div class="container">
	<h1>Login</h1>
	<form action="login" method="post">
 		<div class="mb-3 mt-3">
    		<label for="userId" class="form-label">USER ID:</label>
    		<input type="text" class="form-control" id="userId" placeholder="Enter ID" name="userId">
  		</div>
  		<div class="mb-3 mt-3">
    		<label for="uesrPwd" class="form-label">PASSWORD:</label>
   			<input type="password" class="form-control" id="userPwd" placeholder="Enter password" name="userPwd">
 		</div>
 		
  		<div class="mb-3 mt-3">
   			<input type="checkbox" class="form-check-input" id="remember" name="remember">
    		<label for="remember" class="form-check-label"> REMEMBER</label>
 		</div>
 		
  		<div>
  			<button type="submit" class="btn btn-primary" id="logInBtn" onclick="return validCheck();">Submit</button>
  			<button type="reset" class="btn btn-danger" id="loginResetBtn">Cancel</button>
  		</div>
	</form>
	
	<div><a href="${contextPath }/member/register" class="btn btn-link">회원가입</a></div>
</div>

<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>