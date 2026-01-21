<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding = "UTF-8"%>
<%
//リクエストスコープに保存されたエラーメッセージを取得
String errorMsg = (String) request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>ユーザー登録</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <h1>ユーザー登録</h1>
        <form method="post" action="Register">
            ユーザー名：<input type="text" name="username"><br>
            <p style="color:gray">ユーザー名は3〜20文字の英数字とアンダースコアのみです。</p>
            パスワード：<input type="password" name="password"><br>
            <p style="color:gray">パスワードは8～32文字の大文字・小文字・英数字・記号の組み合わせです。</p>
            <input type="submit" value="登録">
        </form>
        <%if (errorMsg!=null){%>
        <p style="color: red;"><%=errorMsg %></p>
        <%}%>
        <a href ="index.jsp">TOPへ</a>
    </body>
</html>