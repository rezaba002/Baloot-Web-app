<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>400 Error</title>
</head>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
%>
<body>
<h1>400<br><%=errorMessage%></h1>
</body>
</html>
