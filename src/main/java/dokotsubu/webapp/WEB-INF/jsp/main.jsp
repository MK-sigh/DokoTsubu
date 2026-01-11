<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dokotsubu.model.User, dokotsubu.model.Mutter, java.util.List" %>
<%
//セッションスコープに保存されたユーザー情報を取得
User loginUser = (User) session.getAttribute("loginUser");
//リクエストスコープに保存されたつぶやきリストを取得
List<Mutter> mutterList = (List<Mutter>)request.getAttribute("mutterList");
//リクエストスコープに保存されたエラーメッセージを取得
String errorMsg = (String)request.getAttribute("errorMsg");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dokotsubu</title>
</head>
<body>
<h1>Dokotsubu main</h1>
<p><%= loginUser.getName() %>,login now.</p>
<a href= "Logout">logout</a>
<p><a href="Main">update</a></p>
<form action="Main" method="post">
<input type="text" name="text">
<input type="submit" value="mutter">
</form>
<% for (Mutter mutter : mutterList){ %>
<p><%= mutter.getUserName() %> : <%= mutter.getText() %></p>
<% } %>
<% if(errorMsg != null){ %>
<p><%= errorMsg %></p>
<% } %>
</body>
</html>