<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="guest.database.GuestVO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <%@ include file="/include/bs4.jsp" %>
    <style></style>
    <script>
    	'use strict';
    	function checkHomepage() {
    		//Homepage 편집
       		let homepage = document.getElementById("homepage").value;
       		let editHomepage = homepage;
    		if (''!=homepage.trim()) {
    			if (-1!=homepage.indexOf(' ')) {
    				alert('홈페이지명에 공백을 포함할 수 없습니다.');
    				document.getElementById("homepage").focus();
    			} 
    			else {
    		   		if (7 <= homepage.length) {
    		   			editHomepage = homepage.substring(0, homepage.indexOf(':'));
    		   			if (editHomepage == 'https' || editHomepage == 'http' ) {
    		   				editHomepage = homepage.substring(homepage.indexOf('://')+3);
    		   				homepage = editHomepage;
    		   			}
    		   		}
    				document.getElementById("homepage").value = homepage;
    			}
    		}
    		document.getElementById("homepage").value = homepage;
    		document.getElementById("content").focus();
    	}
    	function checkInput() {
    		if (confirm('등록하겠습니까?')) myForm.submit();
    	}
    </script>
</head>
<body>
    <%@ include file="/include/header_home.jsp" %>
    <%@ include file="/include/nav.jsp" %>
<p><br></p>
<div class="container">
  <form name="myForm" method="post" action="${ctxPath}/guestInputOk.gu" class="was-validated">
    <h2>방명록 글쓰기</h2>
    <div class="form-group">
      <label for="name">성명</label>
      <input type="text" class="form-control" name="name" id="name" value="${sNickName==null?'':sNickName}" placeholder="이름을 입력하세요." required autofocus/>
      <div class="valid-feedback"></div>
      <div class="invalid-feedback">성명은 필수 입력사항입니다.</div>
    </div>
    <div class="form-group">
      <label for="email">E-mail</label>
      <input type="text" class="form-control" name="email" id="email" placeholder="E-mail을 입력하세요."/>
      <div class="invalid-feedback">이메일은 선택 입력사항입니다.</div>
    </div>
    <div class="form-group">
      <label for="homepage">Homepage</label>
      <input type="text" class="form-control" name="homepage" id="homepage" placeholder="http://는 빼고 Homepage를 입력하세요." onblur="checkHomepage()"/>
      <div class="invalid-feedback">홈페이지는 선택 입력사항입니다.</div>
    </div>
    
    <div class="form-group">
      <label for="content">방문소감</label>
      <textarea rows="5" class="form-control" name="content" id="content" placeholder="방문소감을 입력하세요." required ></textarea>
      <div class="valid-feedback"></div>
      <div class="invalid-feedback">방문소감은 필수 입력사항입니다.</div>
    </div>
    <div class="form-group">
	    <button type="button" class="btn btn-secondary" onclick="checkInput()">방명록 등록</button> &nbsp;
	    <button type="reset" class="btn btn-secondary">방명록 다시작성</button> &nbsp;
	    <button type="button" class="btn btn-secondary" onclick="location.href='${ctxPath}/guestList.gu';">돌아가기</button>
    </div>
    <input type="hidden" name="hostIp" value="<%=request.getRemoteAddr()%>"/>
  </form>
</div>
    <%@ include file="/include/footer.jsp" %>
</body>
</html>