<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
  <a class="navbar-brand" href="<%=request.getContextPath()%>/">Home</a>
  <!-- <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
    <span class="navbar-toggler-icon"></span>
  </button> -->
  <div class="collapse navbar-collapse" id="collapsibleNavbar">
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/guestList.gu">GUEST</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">BOARD</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">PDS</a>
      </li>
      <li class="nav-item">
        <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">Study</a>
        <div class="dropdown">
			    <div class="dropdown-menu">
			      <a class="dropdown-item" href="<%=request.getContextPath()%>/urlMapping.um">URL확장자(FrontController) 디렉토리패턴</a>
			      <a class="dropdown-item" href="<%=request.getContextPath()%>/urlMapping.url">URL확장자(Controller) 디렉토리패턴</a>
			      <a class="dropdown-item" href="<%=request.getContextPath()%>/study1/urlMapping">URL(Controller) 디렉토리패턴</a>
			      <a class="dropdown-item" href="#">Link 5</a>
			      <a class="dropdown-item" href="#">Link 6</a>
			    </div>
			  </div>
      </li>
    </ul>
  </div>  
</nav>