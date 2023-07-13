<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>200 Success</title>
</head>
<%
    String successMessage = (String) request.getAttribute("successMessage");
%>
<body>
<a href="/">Home</a>
<h1>200<br><%=successMessage%></h1>
</body>
</html>
