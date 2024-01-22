<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<c:set var="contextPath" value="<%=request.getContextPath()%>"></c:set>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script type="text/javascript" src="../js/mypage.js"></script>
<link rel="stylesheet" href="../css/header.css?after">
<link rel="stylesheet" href="../css/mypage.css?after">

<title>My Page</title>
</head>
<body>
	<!-- 로그인 하지 않은 유저는 login.jsp로 돌려보내기 -->
	<c:if test="${sessionScope.login == null }">
		<c:redirect url="./login.jsp"></c:redirect>
	</c:if>


	<jsp:include page="../header.jsp"></jsp:include>

	<div class="container">

		<div class="mb-3 mt-3">
			<img alt="userImg"
				src="${contextPath }/${requestScope.memberInfo.memberImg }">
		</div>


		<div class="mb-3 mt-3">
			<button class="btn btn-success" id="changeImg">Change Image</button>
		</div>
		<div class="changeImg" style="display: none;">
			<form action="updateData.mem" method="post"
				enctype="multipart/form-data">
				<input type="hidden" name="memberImg"
					value="${requestScope.memberInfo.memberImg }"> <input
					type="hidden" name="userImg"
					value="${requestScope.memberInfo.userImg }"> <input
					type="file" class="form-control" id="userImg" name="userImg">
				<button type="submit" class="btn btn-primary"
					data-bs-dismiss="modal">Submit</button>
				<button type="button" class="btn btn-danger fileClose"
					data-bs-dismiss="modal">Close</button>
			</form>
		</div>


		<div class="mb-3 mt-3">
			<div class="input-group mb-3">
				<input type="text" class="form-control" id="userId" readonly
					value="${requestScope.memberInfo.userId }">
				<button class="btn btn-success" type="button" id="changePwdBtn">Change
					PW</button>
			</div>
		</div>

		<div class="mb-3 mt-3">
			<div class="input-group mb-3">
				<input type="text" class="form-control" readonly
					value="${requestScope.memberInfo.userEmail }">
				<button class="btn btn-success" type="submit" id="changeEmailBtn">Change</button>
			</div>
		</div>

		<div class="mb-3 mt-3 pointlog">
			<div class="pointInfo">
				<h3>보유 적립금</h3>
				<p>
					총 적립금 : <span>${requestScope.memberInfo.userPoint } 점</span>
				</p>
										<table class="table table-striped">
								<thead>
									<tr>
										<th>적립 일시</th>
										<th>적립 내용</th>
										<th>적립 포인트</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="point" items="${requestScope.pointlog }">
										<tr>
											<td>${point.actionDate }</td>
											<td>${point.pointType }</td>
											<td>${point.changePoint }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

							<ul class="pagination">
								<c:choose>
									<c:when test="${param.pageNo == 1 || param.pageNo == null}">
										<li class="page-item disabled"><a class="page-link"
											href="#">Previous</a></li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link"
											href="myPage.mem?userId=${requestScope.memberInfo.userId }&pageNo=${requestScope.pageInfo.pageNo - 1 }">Previous</a></li>
									</c:otherwise>
								</c:choose>

								<c:forEach var="paging"
									begin="${requestScope.pageInfo.startPageNum }"
									end="${requestScope.pageInfo.endPageNum }">
									<c:choose>
										<c:when test="${param.pageNo == paging}">
											<li class="page-item active"><a class="page-link"
												href="myPage.mem?userId=${requestScope.memberInfo.userId }&pageNo=${paging }">${paging }</a></li>
										</c:when>
										<c:when test="${param.pageNo == null && paging == 1}">
											<li class="page-item active"><a class="page-link"
												href="myPage.mem?userId=${requestScope.memberInfo.userId }&pageNo=${paging }">${paging }</a></li>
										</c:when>
										<c:otherwise>
											<li class="page-item"><a class="page-link"
												href="myPage.mem?userId=${requestScope.memberInfo.userId }&pageNo=${paging }">${paging }</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<c:choose>
									<c:when
										test="${param.pageNo < requestScope.pageInfo.totalPageCnt }">
										<li class="page-item "><a class="page-link"
											href="myPage.mem?userId=${requestScope.memberInfo.userId }&pageNo=${param.pageNo + 1}">Next</a></li>
									</c:when>
									<c:when test="${param.pageNo == null}">
										<li class="page-item "><a class="page-link"
											href="myPage.mem?userId=${requestScope.memberInfo.userId }&pageNo=2">Next</a></li>
									</c:when>
									<c:otherwise>
										<li class="page-item disabled"><a class="page-link"
											href="#">Next</a></li>
									</c:otherwise>
								</c:choose>

							</ul>
<!-- 				<button type="button" id="pointList" class="btn btn-success">포인트 조회</button> -->
			</div>
		</div>

		<!-- The Modal -->
		<div class="modal changePwd">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- Modal Header -->
					<form action="updateData.mem" method="post"
						enctype="multipart/form-data">
						<div class="modal-header">
							<h4 class="modal-title">비밀번호 변경</h4>
							<button type="button" class="btn-close modalClose"
								data-bs-dismiss="modal"></button>
						</div>

						<!-- Modal body -->
						<div class="modal-body">
							<div class="mb-3 mt-3">
								<label for="uesrPwd" class="form-label">PASSWORD:</label> <input
									type="password" class="form-control" id="userPwd"
									placeholder="Enter password" name="userPwd"> <input
									type="hidden" class="form-control" name="userId"
									value="${requestScope.memberInfo.userId }">
							</div>
							<div class="mb-3 mt-3">
								<label for="uesrPwd2" class="form-label">PASSWORD
									CONFIRM:</label> <input type="password" class="form-control"
									id="userPwd2" placeholder="Enter password">
							</div>

							<!-- Modal footer -->
							<div class="modal-footer">
								<button type="submit" class="btn btn-primary"
									data-bs-dismiss="modal">Submit</button>
								<button type="button" class="btn btn-danger modalClose"
									data-bs-dismiss="modal">Close</button>
							</div>

						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- The Modal -->
		<div class="modal changeEmail">
			<div class="modal-dialog">
				<div class="modal-content">
					<form action="updateData.mem" method="post"
						enctype="multipart/form-data">

						<!-- Modal Header -->
						<div class="modal-header">
							<h4 class="modal-title">이메일 변경</h4>
							<button type="button" class="btn-close modalClose"
								data-bs-dismiss="modal"></button>
						</div>

						<!-- Modal body -->
						<div class="modal-body">
							<div class="mb-3 mt-3">

								<label for="userEmail" class="form-label">EMAIL:</label> <input
									type="text" class="form-control" id="userEmail"
									placeholder="example@distinctao.com" name="userEmail">
								<input type="hidden" class="form-control" name="userId"
									value="${requestScope.memberInfo.userId }">

								<button type="button" class="btn btn-warning" id="sendEmailBtn">Email
									Validate</button>
								<div class="codeDiv" style="display: none;">
									<input type="text" class="form-control" id="emailCode"
										placeholder="Enter Valification Code">
									<button type="button" class="btn btn-warning confirmCode">
										Check Code</button>
								</div>

							</div>
						</div>

						<!-- Modal footer -->
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary"
								data-bs-dismiss="modal">Submit</button>
							<button type="button" class="btn btn-danger modalClose"
								data-bs-dismiss="modal">Close</button>
						</div>

					</form>
				</div>
			</div>
		</div>
		<form action="deleteMember.mem" method="post">
			<input type="hidden" name="deleteMember" value="${requestScope.memberInfo.userId }">
			<button type="submit" class="btn btn-danger">Delete Account</button>
		</form>
		
<!-- 		<!--  포인트 모달 -->
<!-- 		<div class="modal pointList"> -->
<!-- 			<div class="modal-dialog"> -->
<!-- 				<div class="modal-content"> -->

<!-- 					<div class="modal-header"> -->
<!-- 						<h4 class="modal-title">포인트 내역</h4> -->
<!-- 						<button type="button" class="btn-close modalClose" -->
<!-- 							data-bs-dismiss="modal"></button> -->
<!-- 					</div> -->
<!-- 					 모달 바디 -->
<!-- 					<div class="modal-body"> -->
<!-- 						<div class="mb-3 mt-3"> -->
<!-- 							<table class="table table-striped"> -->
<!-- 								<thead> -->
<!-- 									<tr> -->
<!-- 										<th>적립 일시</th> -->
<!-- 										<th>적립 내용</th> -->
<!-- 										<th>적립 포인트</th> -->
<!-- 									</tr> -->
<!-- 								</thead> -->
<!-- 								<tbody> -->
<%-- 									<c:forEach var="point" items="${requestScope.pointlog }"> --%>
<!-- 										<tr> -->
<%-- 											<td>${point.actionDate }</td> --%>
<%-- 											<td>${point.pointType }</td> --%>
<%-- 											<td>${point.changePoint }</td> --%>
<!-- 										</tr> -->
<%-- 									</c:forEach> --%>
<!-- 								</tbody> -->
<!-- 							</table> -->

<!-- 							<ul class="pagination"> -->
<%-- 								<c:choose> --%>
<%-- 									<c:when test="${param.pageNo == 1 || param.pageNo == null}"> --%>
<!-- 										<li class="page-item disabled"><a class="page-link" -->
<!-- 											href="#">Previous</a></li> -->
<%-- 									</c:when> --%>
<%-- 									<c:otherwise> --%>
<!-- 										<li class="page-item"><a class="page-link" -->
<%-- 											href="myPage.mem?userId=${requestScope.memberInfo.userId }&pageNo=${requestScope.pageInfo.pageNo - 1 }">Previous</a></li> --%>
<%-- 									</c:otherwise> --%>
<%-- 								</c:choose> --%>

<%-- 								<c:forEach var="paging" --%>
<%-- 									begin="${requestScope.pageInfo.startPageNum }" --%>
<%-- 									end="${requestScope.pageInfo.endPageNum }"> --%>
<%-- 									<c:choose> --%>
<%-- 										<c:when test="${param.pageNo == paging}"> --%>
<!-- 											<li class="page-item active"><a class="page-link" -->
<%-- 												href="myPage.mem?userId=${requestScope.memberInfo.userId }&pageNo=${paging }">${paging }</a></li> --%>
<%-- 										</c:when> --%>
<%-- 										<c:when test="${param.pageNo == null && paging == 1}"> --%>
<!-- 											<li class="page-item active"><a class="page-link" -->
<%-- 												href="myPage.mem?userId=${requestScope.memberInfo.userId }&pageNo=${paging }">${paging }</a></li> --%>
<%-- 										</c:when> --%>
<%-- 										<c:otherwise> --%>
<!-- 											<li class="page-item"><a class="page-link" -->
<%-- 												href="myPage.mem?userId=${requestScope.memberInfo.userId }&pageNo=${paging }">${paging }</a></li> --%>
<%-- 										</c:otherwise> --%>
<%-- 									</c:choose> --%>
<%-- 								</c:forEach> --%>
<%-- 								<c:choose> --%>
<%-- 									<c:when --%>
<%-- 										test="${param.pageNo < requestScope.pageInfo.totalPageCnt }"> --%>
<!-- 										<li class="page-item "><a class="page-link" -->
<%-- 											href="myPage.mem?userId=${requestScope.memberInfo.userId }&pageNo=${param.pageNo + 1}">Next</a></li> --%>
<%-- 									</c:when> --%>
<%-- 									<c:when test="${param.pageNo == null}"> --%>
<!-- 										<li class="page-item "><a class="page-link" -->
<%-- 											href="myPage.mem?userId=${requestScope.memberInfo.userId }&pageNo=2">Next</a></li> --%>
<%-- 									</c:when> --%>
<%-- 									<c:otherwise> --%>
<!-- 										<li class="page-item disabled"><a class="page-link" -->
<!-- 											href="#">Next</a></li> -->
<%-- 									</c:otherwise> --%>
<%-- 								</c:choose> --%>

<!-- 							</ul> -->

<!-- 						</div> -->
<!-- 					</div> -->

<!-- 					Modal footer -->
<!-- 					<div class="modal-footer"> -->
<!-- 						<button type="button" class="btn btn-danger modalClose" -->
<!-- 							data-bs-dismiss="modal">Close</button> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>