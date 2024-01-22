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
<title>Insert title here</title>
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>

<div class="container">
	<h1>Login</h1>
	<form action="login.mem" method="post">
 		<div class="mb-3 mt-3">
    		<label for="userId" class="form-label">USER ID:</label>
    		<input type="text" class="form-control" id="userId" placeholder="Enter ID" name="userId">
  		</div>
  		<div class="mb-3 mt-3">
    		<label for="uesrPwd" class="form-label">PASSWORD:</label>
   			<input type="password" class="form-control" id="userPwd" placeholder="Enter password" name="userPwd">
 		</div>
  		<div>
  			<button type="submit" class="btn btn-primary" id="logInBtn" onclick="return validCheck();">Submit</button>
  			<button type="reset" class="btn btn-danger" id="loginResetBtn">Cancel</button>
  		</div>
	</form>
	
	<div><a href="${contextPath }/member/register.jsp" class="btn btn-link">회원가입</a></div>
</div>

<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>