<%@ page import=" org.example.classes.Store "%>
<%@ page import="org.example.classes.Provider" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Providers</title>
    <style>
        table {
            width: 100%;
            text-align: center;
            padding: 0 50px;
        }
    </style>
</head>
<%
    Store store = Store.getInstance();
    String username = store.getCurrentUser().getUsername();
%>
<body>
<a href="/">Home</a>
<h3>username: <%=username%></h3>
<br><br>
<table>
    <caption>
        <h2>Providers</h2>
    </caption>
    <tbody>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Register Date</th>
        <th>Links</th>
    </tr>
    <%
        for (Provider providerObj : store.getProviders()){
    %>
    <tr>
        <td><%= providerObj.getId()%></td>
        <td><%= providerObj.getName()%></td>
        <td><%= providerObj.getRegistryDate()%></td>
        <td><a href="/providers/<%= providerObj.getId() %>">Link</a></td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
</body>
</html>
