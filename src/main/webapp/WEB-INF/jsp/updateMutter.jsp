<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dokotsubu.model.Mutter" %>
<%
//リクエストスコープ取得
Mutter mutter = (Mutter) request.getAttribute("mutter");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Dokotsubu</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <h1>編集画面</h1>

        <table border="1" style="border-collapse: collapse; width: 100%; margin-bottom:20px;">
            <tr style="background-color: #f2f2f2;">
                <th>ユーザー名</th>
                <th>つぶやき内容</th>
            </tr>
            <tr>
                <td><%= mutter.getUserName() %></td>
                <td><%= mutter.getText() %></td>
            </tr>
        </table>


        <form action="Update" method="post" style="display: inline;">
            <input type="hidden" name="id" value="<%= mutter.getId()%>">
            <input type="text" name="text" style="width: 100%; max-width: 500px; padding: 10px;">
            <input type="submit" value="編集">
        </form>
    </body>
</html>