<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" %>
<%@ page import="pChat.model.User" %>
<%
// セッションスコープからユーザー情報を取得
User loginUser = (User) session.getAttribute("loginUser");
//リクエストスコープに保存されたエラーメッセージを取得
String errorMsg = (String) request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>どこつぶ</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  </head>
  <body>
    <h1>どこつぶログイン</h1>
    <% if(loginUser != null) { %>
      <p>ログインに成功しました</p>
      <p>ようこそ<%= loginUser.getName() %>さん</p>
      <a href="<%= request.getContextPath() %>/app/Main">投稿・閲覧へ</a>
    <% } else { %>
      <p>ログインに失敗しました</p>
      <p style="color:red"><%= errorMsg %></p>
      <a href="index.jsp">TOPへ</a>
    <% } %>
  </body>
</html>