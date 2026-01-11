<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dokotsubu</title>
</head>
<body>
<p>Welcome to Dokotsubu !!</p>
<p style="padding-bottom: 10px;"><a href= "Register">ユーザー未登録の方はこちら</a> </p>
<form action = "Login" method = "post">
UserName: <input type="text" name="name"><br>
PW: <input type="password" name="pass"><br>
<input type="submit" value="ログイン">
</form>
</body>
</html>