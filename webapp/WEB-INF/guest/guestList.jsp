<%@page import="java.util.List"%>
<%@page import="guest.database.GuestVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String sAdmin = (String)session.getAttribute("sAdmin");
	List<GuestVO> vos = (List<GuestVO>)request.getAttribute("vos");
	GuestVO vo = null;
%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
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
<%
	if (null != sAdmin && sAdmin.equals("adminOk")) {
%>
		<div class="col text-left"><a href="${ctxPath}/adminLogout.gu" class="btn btn-secondary">관리자 로그아웃</a></div>
<%	
	} else {
%>
		<div class="col text-left"><a href="${ctxPath}/adminLogin.gu" class="btn btn-secondary">관리자</a></div>
<% 	} %>
		<div class="col text-right"><a href="${ctxPath}/guestInput.gu" class="btn btn-secondary">글쓰기</a></div>
	</div>
<%
	String vDate = null;
	String content = null;
	String email = null;
	String homepage = null;
	int no = vos.size();
	for (int i=0; i<vos.size(); i++) {
		vo = vos.get(i);
		
		vDate = vo.getvDate();
		content = vo.getContent().replace("\n", "<br>");
		email = vo.getEmail();
		homepage = vo.getHomepage();
		if (null == email) email = "- 없음 -";
		if (null == homepage) homepage = "- 없음 -";
		else homepage = "<a href='"+homepage+"' target='_blank'>"+homepage+"</a>";
%>
		<table class="table table-borderless m-0 p-0">
			<tr>
				<td class="text-left">방문번호 : <%=no%>
<%	if (null != sAdmin && sAdmin.equals("adminOk")) { %>
				<%-- [<a href="${ctxPath}/guestDelete.gu?idx=<%=vo.getIdx()%>">삭제</a>] --%>
				[<a href="javascript:delCheck('<%= vo.getIdx()%>');">삭제</a>]
<%	} %>
				</td>
				<td class="text-right">방문IP : <%=vo.getHostIp()%></td>
			</tr>
			<tr>
				<th width="16%" class="text-center">성명</th>
				<td width="34%"><%=vo.getName()%></td>
				<th width="16%" class="text-center">방문일자</th>
				<td width="34%"><%=vDate%></td>
			</tr>
			<tr>
				<th class="text-center">전자우편</th>
				<td colspan="3"><%=email%></td>
			</tr>
			<tr>
				<th class="text-center">홈페이지</th>
				<td colspan="3"><%=homepage%></td>
			</tr>
			<tr>
				<th class="text-center">글내용</th>
				<td colspan="3" style="height:150px"><%=content%></td>
			</tr>
		</table>
<% 		no--;
	} 
%>
</div>
    <%@ include file="/include/footer.jsp" %>
</body>
</html>