<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
  <form method="post" action="/login">
    <label>Username:</label>
    <input name="username" type="text"/>
    <br>
    <label>Password:</label>
    <input name="password" type="text"/>
    <br>
    <button type="submit">Login!</button>
  </form>
</body>
</html>
