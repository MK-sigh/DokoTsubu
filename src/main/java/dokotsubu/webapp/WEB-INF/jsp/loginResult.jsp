<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dokotsubu.model.User" %>
<%
//セッションスコープからユーザーを取得
User loginUser = (User) session.getAttribute("loginUser");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dokotsubu</title>
</head>
<body>
<h1>Dokotsubu Login</h1>
<% if (loginUser != null){ %>
<p>Login suceeded.</p>
<p>Welcome <%= loginUser.getName() %>!!</p>
<a href= "Main">see & submit</a>
<% }else{ %>
<p>Login failed.</p>
<a href ="index.jsp">TOP</a>
<% } %>
</body>
</html>