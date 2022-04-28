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
    <style></style>
    <script>
    	'use strict';
    	
    </script>
</head>
<body>
    <%@ include file="/include/header_home.jsp" %>
    <%@ include file="/include/nav.jsp" %>
<p><br></p>
<div class="container">
	<h2>방 명 록</h2>
	<div>
		<a href="#">글쓰기</a>
	</div>
<%	for (int i=0; i<vos.size(); i++) {
		vo = vos.get(i);
%>
		방문번호 : <%=vo.getIdx()%><br>
		성명 : <%=vo.getName()%><br>
		방문일자 : <%=vo.getvDate()%><br>
		이메일 : <%=vo.getEmail()%><br>
		방문소감 : <%=vo.getContent()%><br>
<% 	} %>

</div>
    <%@ include file="/include/footer.jsp" %>
</body>
</html>