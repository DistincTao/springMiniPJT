<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script type="text/javascript" src="../resources/js/login.js"></script>
<link rel="stylesheet" href="/resources/css/header.css?after">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="./header.jsp"></jsp:include>
<div class="container">
	<h1>Error</h1>
<div>${errorMsg }</div>
<hr>
<c:forEach var="errorStack" items="${errorStack }">
	<div style="color : red;">${errorStack }</div>
</c:forEach>



</div>
<jsp:include page="./footer.jsp"></jsp:include>
</body>
</html>