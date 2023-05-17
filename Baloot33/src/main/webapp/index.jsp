<%@ page import="org.example.classes.Store" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
</head>
<%
    Store store = Store.getInstance();
    String username = store.getCurrentUser().getUsername();
%>
<body>
    <h1>Welcome to Baloot</h1>
<%
    if (username == null) {
%>
    <ul>
        <li><a href="login">Log In</a></li>
    </ul>
<%
    }
    else {
%>
    <ul>
        <li>username: <%=username%></li>
        <li><a href="commodities">Commodities</a></li>
        <li><a href="providers">Providers</a></li>
        <li><a href="buyList">Buy List</a></li>
        <li><a href="credit">Add Credit</a></li>
        <li><a href="logout">Log Out</a></li>
    </ul>
<%
    }
%>
</body>
</html>
