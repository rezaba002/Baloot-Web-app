<%@ page import="org.example.classes.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String id = (String) request.getAttribute("id");
    int providerId = Integer.valueOf(id);
    Store store = Store.getInstance();
    String username = store.getCurrentUser().getUsername();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Provider <%=providerId%></title>
    <style>
        li {
            padding: 5px;
        }
        table {
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<body>
    <a href="/">Home</a>
    <br>
    <a href="/providers">Providers</a>
    <h3>username: <%=username%></h3>
    <ul>
<%
    for (Provider providerObj : store.getProviders()) {
        if (providerObj.getId() == providerId) {
%>
        <li id="id">Id: <%=providerObj.getId()%></li>
        <li id="name">Name: <%=providerObj.getName()%></li>
        <li id="inStock">Register Date: <%=providerObj.getRegistryDate()%></li>
    </ul>
    <br>
    <table>
        <caption><h2>Provided Commodities</h2></caption>
        <tbody>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Price</th>
            <th>Categories</th>
            <th>Rating</th>
            <th>In Stock</th>
            <th>Links</th>
        </tr>
<%
    for (Commodity commodityObj : store.getCommodities()){
        if (commodityObj.getProviderId() == providerId){
            int i = 0;
            String tempCategories = "";
            for (i = 0; i < commodityObj.getCategories().size(); i++) {
                tempCategories += commodityObj.getCategories().get(i);
                if (i < commodityObj.getCategories().size() - 1)
                    tempCategories += ", ";
            }
%>
        <tr>
            <td><%=commodityObj.getId()%></td>
            <td><%=commodityObj.getName()%></td>
            <td><%=commodityObj.getPrice()%></td>
            <td><%=tempCategories%></td>
            <td><%=commodityObj.getRating()%></td>
            <td><%=commodityObj.getInStock()%></td>
            <td><a href="/commodities/<%=commodityObj.getId()%>">Link</a></td>
        </tr>
<%
        }
    }
        }
    }
%>
        </tbody>
    </table>
</body>
</html>
