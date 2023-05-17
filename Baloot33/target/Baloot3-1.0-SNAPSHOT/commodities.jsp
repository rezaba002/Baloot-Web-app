<%@ page import=" org.example.classes.Commodity "%>
<%@ page import=" org.example.classes.Store "%>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Commodities</title>
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
    String category = (String) request.getAttribute("category");
    String name = (String) request.getAttribute("name");
    String rate = (String) request.getAttribute("rate");
    String price = (String) request.getAttribute("price");
%>
<body>
    <a href="/">Home</a>
    <h3>username: <%=username%></h3>
    <br><br>
    <form action="" method="POST">
        <label>Search:</label>
        <input type="text" name="search" value="">
        <button type="submit" name="action" value="search_by_category" formaction="/commodities">Search By Category</button>
        <button type="submit" name="action" value="search_by_name" formaction="/commodities">Search By Name</button>
        <button type="submit" name="action" value="clear" formaction="/commodities">Clear Search</button>
    </form>
    <br><br>
    <form action="" method="POST">
        <label>Sort By:</label>
        <button type="submit" name="action" value="sort_by_rate" formaction="/commodities">Rate</button>
        <button type="submit" name="action" value="sort_by_price" formaction="/commodities">Price</button>
    </form>
    <br><br>
    <table>
        <caption>
            <h2>Commodities</h2>
        </caption>
        <tbody>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Provider Name</th>
            <th>Price</th>
            <th>Categories</th>
            <th>Rating</th>
            <th>In Stock</th>
            <th>Links</th>
        </tr>

    <%
        if (category == null && name == null && rate == null && price == null) {
            ArrayList<Commodity> temp = new ArrayList<Commodity>();
            for (Commodity commodityObj : store.getCommodities()){
            temp.add(commodityObj);
            int i = 0;
            String tempCategories = "";
            for (i=0;i<commodityObj.getCategories().size();i++){
                tempCategories += commodityObj.getCategories().get(i);
                if (i<commodityObj.getCategories().size() - 1)
                    tempCategories += ", ";
            }
    %>
        <tr>
            <td><%= commodityObj.getId()%></td>
            <td><%= commodityObj.getName()%></td>
            <td><%= commodityObj.getProviderId()%></td>
            <td><%= commodityObj.getPrice()%></td>
            <td><%= tempCategories %></td>
            <td><%= commodityObj.getRating()%></td>
            <td><%= commodityObj.getInStock()%></td>
            <td><a href="/commodities/<%= commodityObj.getId() %>">Link</a></td>
        </tr>
    <%
            }
        store.getTempCommodities().clear();
        store.setTempCommodities(temp);
        }

        else if (category != null && name.equalsIgnoreCase("#")) {
            ArrayList<Commodity> temp = new ArrayList<Commodity>();
            for (Commodity commodityObj : store.getCommodities()){
                if (commodityObj.getCategories().contains(category)) {
                    temp.add(commodityObj);
                    int i = 0;
                    String tempCategories = "";
                    for (i = 0; i < commodityObj.getCategories().size(); i++) {
                        tempCategories += commodityObj.getCategories().get(i);
                        if (i < commodityObj.getCategories().size() - 1)
                            tempCategories += ", ";
                    }
    %>
        <tr>
            <td><%= commodityObj.getId()%></td>
            <td><%= commodityObj.getName()%></td>
            <td><%= commodityObj.getProviderId()%></td>
            <td><%= commodityObj.getPrice()%></td>
            <td><%= tempCategories %></td>
            <td><%= commodityObj.getRating()%></td>
            <td><%= commodityObj.getInStock()%></td>
            <td><a href="/commodities/<%= commodityObj.getId() %>">Link</a></td>
        </tr>
    <%
                }
            }
        store.getTempCommodities().clear();
        store.setTempCommodities(temp);
        }

        else if (name != null && category.equalsIgnoreCase("#")) {
            ArrayList<Commodity> temp = new ArrayList<Commodity>();
            for (Commodity commodityObj : store.getCommodities()){
                if (commodityObj.getName().contains(name)) {
                    temp.add(commodityObj);
                    int i = 0;
                    String tempCategories = "";
                    for (i = 0; i < commodityObj.getCategories().size(); i++) {
                        tempCategories += commodityObj.getCategories().get(i);
                        if (i < commodityObj.getCategories().size() - 1)
                            tempCategories += ", ";
                    }
    %>
        <tr>
            <td><%= commodityObj.getId()%></td>
            <td><%= commodityObj.getName()%></td>
            <td><%= commodityObj.getProviderId()%></td>
            <td><%= commodityObj.getPrice()%></td>
            <td><%= tempCategories %></td>
            <td><%= commodityObj.getRating()%></td>
            <td><%= commodityObj.getInStock()%></td>
            <td><a href="/commodities/<%= commodityObj.getId() %>">Link</a></td>
        </tr>
    <%
                }
            }
        store.getTempCommodities().clear();
        store.setTempCommodities(temp);
        }

        else if (rate.equalsIgnoreCase("true")) {
            store.sortCommoditiesByRating();
            for (Commodity commodityObj : store.getTempCommodities()){
                int i = 0;
                String tempCategories = "";
                for (i=0;i<commodityObj.getCategories().size();i++){
                    tempCategories += commodityObj.getCategories().get(i);
                    if (i<commodityObj.getCategories().size() - 1)
                        tempCategories += ", ";
                }
    %>
        <tr>
            <td><%=commodityObj.getId()%></td>
            <td><%=commodityObj.getName()%></td>
            <td><%=commodityObj.getProviderId()%></td>
            <td><%=commodityObj.getPrice()%></td>
            <td><%=tempCategories %></td>
            <td><%=commodityObj.getRating()%></td>
            <td><%=commodityObj.getInStock()%></td>
            <td><a href="/commodities/<%=commodityObj.getId()%>">Link</a></td>
        </tr>
    <%
            }
        }

        else if (price.equalsIgnoreCase("true")) {
            store.sortCommoditiesByPrice();
            for (Commodity commodityObj : store.getTempCommodities()){
                int i = 0;
                String tempCategories = "";
                for (i=0;i<commodityObj.getCategories().size();i++){
                    tempCategories += commodityObj.getCategories().get(i);
                    if (i<commodityObj.getCategories().size() - 1)
                        tempCategories += ", ";
                }
    %>
        <tr>
            <td><%= commodityObj.getId()%></td>
            <td><%= commodityObj.getName()%></td>
            <td><%= commodityObj.getProviderId()%></td>
            <td><%= commodityObj.getPrice()%></td>
            <td><%= tempCategories %></td>
            <td><%= commodityObj.getRating()%></td>
            <td><%= commodityObj.getInStock()%></td>
            <td><a href="/commodities/<%= commodityObj.getId() %>">Link</a></td>
        </tr>
    <%
            }
        }
    %>
        </tbody>
    </table>
</body>
</html>
