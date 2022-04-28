<%@page import="java.util.List"%>
<%@page import="guest.database.GuestVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
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
    	
    </script>
</head>
<body>
    <%@ include file="/include/header_home.jsp" %>
    <%@ include file="/include/nav.jsp" %>
<p><br></p>
<div class="container">
	<h2 class="text-center">방 명 록</h2>
	<div class="text-right m-2">
		<a href="${ctxPath}/guestInput.gu" class="btn btn-secondary">글쓰기</a>
	</div>
<%	for (int i=0; i<vos.size(); i++) {
		vo = vos.get(i);
		
		String vDate = vo.getvDate();
%>
		<table class="table table-borderless m-0 p-0">
			<tr>
				<td class="text-left">방문번호 : <%=vo.getIdx()%></td>
				<td class="text-right">방문IP : <%=vo.getHostIp()%></td>
			</tr>
		</table>
		<table class="table table-bordered">
			<tr>
				<th width="16%" class="text-center">성명</th>
				<td width="34%"><%=vo.getName()%></td>
				<th width="16%" class="text-center">방문일자</th>
				<td width="34%"><%=vo.getvDate()%></td>
			</tr>
			<tr>
				<th class="text-center">이메일</th>
				<td colspan="3"><%=vo.getEmail()%></td>
			</tr>
			<tr>
				<th class="text-center">방문소감</th>
				<td colspan="3"><%=vo.getContent()%></td>
			</tr>
		</table>
<% 	} %>

</div>
    <%@ include file="/include/footer.jsp" %>
</body>
</html>