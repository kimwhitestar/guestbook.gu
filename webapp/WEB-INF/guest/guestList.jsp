<%@ page import="java.util.List"%>
<%@ page import="guest.database.GuestVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<c:set var="sAdmin" value="${sAdmin}" scope="session" />
<c:set var="pageNo" value="${pageNo}" scope="request" />
<c:set var="pageSize" value="${pageSize}" scope="request" />
<c:set var="totPage" value="${totPage}" scope="request" />
<c:set var="curScrStartNo" value="${curScrStartNo}" scope="request" />
<c:set var="LF" value="\n" scope="page" />
<c:set var="BR" value="<br>" scope="page" />

<c:set var="vos" value="${vos}" scope="request" />
<c:set var="no" value="${vos.size()}" scope="page" />
<%--
	List<GuestVO> vos = (List<GuestVO>)request.getAttribute("vos");
	int no = vos.size();
--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>guestList.jsp</title>
    <%@ include file="/include/bs4.jsp" %>
    <style>
    	th {background-color: orange;}
    </style>
    <script>
    	'use strict';
    	function delCheck(idx) {
    		let ans = confirm('게시글을 삭제하겠습니까?');
    		if (ans) location.href = '${ctxPath}/guestDelete.gu?idx='+idx;
    	}
    </script>
</head>
<body>
    <%@ include file="/include/header_home.jsp" %>
    <%@ include file="/include/nav.jsp" %>
<p><br></p>
<div class="container">
	<h2 class="text-center">방 명 록</h2>
	<div class="m-2 row">
<c:if test="${sAdmin != null && sAdmin == 'adminOk'}">
		<div class="col text-left"><a href="${ctxPath}/adminLogout.gu" class="btn btn-secondary">관리자 로그아웃</a></div>
</c:if>
<c:if test="${sAdmin == null || sAdmin != 'adminOk'}">
		<div class="col text-left"><a href="${ctxPath}/adminLogin.gu" class="btn btn-secondary">관리자</a></div>
</c:if>
		<div class="col text-right"><a href="${ctxPath}/guestInput.gu" class="btn btn-secondary">글쓰기</a></div>
	</div>
	
<c:forEach items="${vos}" var="vo" varStatus="idxNo">
		<table class="table table-borderless m-0 p-0">
			<tr>
				<td class="text-left">방문번호 : ${curScrStartNo - (idxNo.count-1)}
	<c:if test="${sAdmin != null && sAdmin == 'adminOk'}">
				<%-- [<a href="${ctxPath}/guestDelete.gu?idx=<%=vo.getIdx()%>">삭제</a>] --%>
				[<a href="javascript:delCheck('${vo.idx}');">삭제</a>]
	</c:if>
				</td>
				<td class="text-right">방문IP : ${vo.hostIp}</td>
			</tr>
			<tr>
				<th width="16%" class="text-center">성명</th>
				<td width="34%">${vo.name}</td>
				<th width="16%" class="text-center">방문일자</th>
				<td width="34%">${vo.vDate}</td>
			</tr>
			<tr>
				<th class="text-center">전자우편</th>
	<c:if test="${vo.email eq null}">
				<td colspan="3">- 없음 -</td>
	</c:if>
	<c:if test="${vo.email ne null}">
				<td colspan="3">${vo.email}</td>
	</c:if>
			</tr>
			<tr>
				<th class="text-center">홈페이지</th>
	<c:if test="${vo.homepage eq null}">
				<td colspan="3">- 없음 -</td>
	</c:if>				
	<c:if test="${vo.homepage ne null}">
				<td colspan="3"><a href='http://${vo.homepage}' target='_blank'>http://${vo.homepage}</a></td>
	</c:if>
			</tr>
			<tr>
				<th class="text-center">글내용</th>
				<td colspan='3' style='height:150px'>${vo.content.replace(LF,BR)}</td>
			</tr>
		</table>
</c:forEach>	


		<!-- 페이징 처리 -->
		<div class="text-center">
			[<a href='guestList.gu?pageNo=1'>첫페이지</a>]
<c:if test="${pageNo > 1}">
				[<a href='guestList.gu?pageNo=${pageNo - 1}'>이전페이지</a>]
</c:if>
				${pageNo}Page / ${totPage}Pages
<c:if test="${pageNo != totPage}">
				[<a href='guestList.gu?pageNo=${pageNo + 1}'>다음페이지</a>]
</c:if>
			[<a href='guestList.gu?pageNo=${totPage}'>마지막페이지</a>]
		</div>
	</div>
    <%@ include file="/include/footer.jsp" %>
</body>
</html>