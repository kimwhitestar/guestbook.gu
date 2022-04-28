<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String msg = (String) request.getAttribute("msg");
  String url = (String) request.getAttribute("url");
%><!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>message.jsp</title>
    <script>
    'use strict';
    //JavaScript 변수는 server실행일 경우 el표기법으로 사용가능하나, client실행일 경우는 순차가 안맞아서 사용안됨
    let msg = '${msg}';
    if (msg == 'loginIdCheckNo') msg = "회원ID 중복입니다.새로운 회원ID를 입력하세요";
    else if (msg == 'loginJoinOk') msg = "회원 가입됬습니다.";
    else if (msg == 'loginJoinNo') msg = "회원 가입 실패~~.";
    else if (msg == 'loginUpdateOk') msg = "회원정보 수정됬습니다.";
    else if (msg == 'loginUpdateNo') msg = "회원정보 수정 실패~~.";
    else if (msg == 'loginDeleteOk') msg = "회원 탈퇴됬습니다.";
    else if (msg == 'loginDeleteNo') msg = "회원 탈퇴 실패~~.";
    else if (msg == 'guestInputOk') msg = "방명록에 등록습니다.";
    else if (msg == 'guestInputNo') msg = "방명록 게시 실패~~.";
    else if (msg == 'guestDeleteOk') msg = "게시글이 삭제됬습니다.";
    else if (msg == 'guestDeleteNo') msg = "게시글 삭제 실패~~";
    else if (msg == 'adminOk') msg = "관리자 인증됬습니다";
    else if (msg == 'adminNo') msg = "관리자 인증 실패~~.";
    else if (msg == 'adminLogoutOk') msg = "관리자 로그아웃 되었습니다";
    
    alert(msg);
    if ('${url}' != '') location.href = '${url}';
    </script>
</head>
<body>
</body>
</html>