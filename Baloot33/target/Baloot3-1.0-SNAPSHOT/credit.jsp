<%@ page import="org.example.classes.Store" %>
<%@ page import="org.example.classes.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Credit</title>
</head>
<%
    Store store = Store.getInstance();
    String username = store.getCurrentUser().getUsername();

    int userCredit = 0;
    for (User userObj : store.getUsers()) {
        if (userObj.getUsername().equalsIgnoreCase(username))
            userCredit = userObj.getCredit();
    }
%>
<body>
    <a href="/">Home</a>
    <br>
    <span>username: <%=username%></span>
    <br>
    <span>credit: <%=userCredit%></span>
    <br>
    <form method="post" action="">
        <label>Credits:</label>
        <input name="credit" type="number" min="0" />
        <br>
        <button type="submit">Add credits</button>
    </form>
</body>
</html>
