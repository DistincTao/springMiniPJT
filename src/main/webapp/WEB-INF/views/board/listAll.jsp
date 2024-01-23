<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<c:set var="contextPath" value="<%=request.getContextPath()%>"></c:set>
<c:set var="now" value="<%=new java.util.Date()%>" />
<fmt:formatDate value="${board.postDate }" pattern="yyyy-MM-dd HH:m::ss" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Board List</title>
<link rel="stylesheet" href="/resources/css/header.css?after">
<link rel="stylesheet" href="/resources/css/listAll.css?after">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/listAll.js"></script>
<style>
.searchArea {
	display : flex;
	justify-content: flex-end;
}
</style>

</head>
<body>
	<jsp:include page="../header.jsp"></jsp:include>
	${contextPath }
	<div class="container">	
		<h1>게시판 전체 목록</h1>
		<form action="listAll.bo" class="searchArea">
			<div style="width : 150px;">
				<select class="form-select" name="searchType">
					<option value="writer">Writer</option>
					<option value="title">Title</option>
					<option value="content">Content</option>
				</select>
			</div>
			<div class="input-group mb-3" style="width : 350px">
				<input type="text" class="form-control" id="searchWord" name="searchWord" placeholder="What do you want to search?">
				<button class="btn btn-primary" type="submit">Search</button>
				<button class="btn btn-danger" type="reset">Cancel</button>
			</div>
		</form>
		<div class="boardList">
			<c:choose>
				<c:when test="${boardList != null }">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>글번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일</th>
								<th>조회수</th>
								<th>좋아요</th>
							</tr>
						</thead>
						<tbody>

							<c:forEach var="board" items="${boardList }">
								<c:choose>
									<c:when test="${board.isDelete == 'N' && board.step == 0}">
										<tr id="board${board.boardNo }" class="board"
											onclick="location.href='viewBoard?boardNo=${board.boardNo}'">
											<td>${board.boardNo }</td>
											<td>${board.title }</td>
											<td>${board.writer }</td>
											<td><fmt:formatDate value="${board.postDate }"
													pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td>${board.readCount }</td>
											<td>${board.likeCount }</td>
										</tr>
									</c:when>
									<c:when test="${board.isDelete == 'N' && board.step != 0}">
										<tr id="board${board.boardNo }" class="board"
											onclick="location.href='viewBoard?boardNo=${board.boardNo}'">
											<td>${board.boardNo }</td>
											<td><c:forEach begin="1" end="${board.step}">
												&nbsp; 
											</c:forEach> <img src="${contextPath }/resources/img/arrow.png"
												style="width: 20px; height: 20px;"> ${board.title }</td>
											<td>${board.writer }</td>
											<td><fmt:formatDate value="${board.postDate }"
													pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td>${board.readCount }</td>
											<td>${board.likeCount }</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr id="board${board.boardNo }" class="deletedBoard">
											<td></td>
											<td>삭제된 글입니다.</td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tbody>
					</table>

				</c:when>
				<c:otherwise>
				<img src="/resources/img/box.png">
			</c:otherwise>
			</c:choose>
			<div class="btns">
				<button type="button" class="btn btn-primary"
					onclick="location.href='writeBoard'">글쓰기</button>

			</div>
				
			<ul class="pagination">
				<c:choose>
					<c:when test="${param.pageNo == 1 || param.pageNo == null}">
						<li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link"
							href="listAll.bo?pageNo=${param.pageNo - 1}&searchType=${param.searchType }&searchWord=${param.searchWord }">Previous</a></li>
					</c:otherwise>
				</c:choose>

				<c:forEach var="paging"
					begin="${requestScope.pageInfo.startPageNum }"
					end="${requestScope.pageInfo.endPageNum }">
					<c:choose>
						<c:when test="${param.pageNo == paging}">
							<li class="page-item active"><a class="page-link"
								href="listAll.bo?pageNo=${paging }&searchType=${param.searchType }&searchWord=${param.searchWord }">${paging }</a></li>
						</c:when>
						<c:when test="${param.pageNo == null && paging == 1}">
							<li class="page-item active"><a class="page-link"
								href="listAll.bo?pageNo=${paging }&searchType=${param.searchType }&searchWord=${param.searchWord }">${paging }</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link"
								href="listAll.bo?pageNo=${paging }&searchType=${param.searchType }&searchWord=${param.searchWord }">${paging }</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:choose>
					<c:when
						test="${param.pageNo < requestScope.pageInfo.totalPageCnt }">
						<li class="page-item "><a class="page-link"
							href="listAll.bo?pageNo=${param.pageNo + 1}&searchType=${param.searchType }&searchWord=${param.searchWord }">Next</a></li>
					</c:when>
					<c:when test="${param.pageNo == null && param.pageNo < requestScope.pageInfo.totalPageCnt }">
						<li class="page-item "><a class="page-link"
							href="listAll.bo?pageNo=2&searchType=${param.searchType }&searchWord=${param.searchWord }">Next</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item disabled"><a class="page-link" href="#">Next</a></li>
					</c:otherwise>
				</c:choose>

			</ul>
		</div>


	</div>

	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>