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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <h1>Dokotsubu main</h1>
        <p style="margin-bottom: 20px;">
        <% if(loginUser != null){ %>
        <%= loginUser.getName() %>
        <% } %>
        さんでログイン中...</p>
        <a href= "Logout" style="padding: 5px 10px; background-color: #eee; text-decoration: none; border-radius: 4px;">ログアウト</a>
        <p style="margin-bottom: 20px;">
            <a href="Main" style="padding: 5px 10px; background-color: #eee; text-decoration: none; border-radius: 4px;">更新</a>
        </p>
        <div style="display: flex; flex-direction: column; gap: 15px;">
            <form action="Search" method="post">
                <input type="text" name="keyword">
                <input type="submit" value="検索">
            </form>

            <form action="Main" method="post" style="margin-bottom: 20px;">
                <input type="text" name="text">
                <input type="submit" value="つぶやく">
            </form>
        </div>
        <table border="1" style="border-collapse: collapse; width: 100%;">
            <tr style="background-color: #f2f2f2;">
                <th>ユーザー名</th>
                <th>つぶやき内容</th>
                <th>操作</th>
            </tr>
            <%if (mutterList != null && mutterList.size() > 0){%>
                <% for (Mutter mutter : mutterList){ %>
                    <tr>
                        <td><%= mutter.getUserName() %></td>
                        <td><%= mutter.getText() %></td>
                        <td>
                            <form action="Update" method="get" style="display: inline;">
                                <input type="hidden" name="id" value="<%= mutter.getId()%>">
                                <input type="submit" value="編集">
                            </form>
                            <form action="Delete" method="post" 
                                onsubmit="return confirm('本当に削除してもよろしいですか？');" style="display: inline;">
                                <input type="hidden" name="id" value="<%= mutter.getId()%>">
                                <input type="submit" value="削除">
                            </form>
                        </td>
                    </tr>
                <% } %>
            </table>
            <%}else{%>
                <p>つぶやきはありません。</p>
            <%} %>
        <% if(errorMsg != null){ %>
        <p><%= errorMsg %></p>
        <% } %>
    </body>
</html>