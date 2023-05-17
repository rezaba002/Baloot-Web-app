<%@ page import="org.example.classes.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  String id = (String) request.getAttribute("id");
  int commodityId = Integer.valueOf(id);
  Store store = Store.getInstance();
  String username = store.getCurrentUser().getUsername();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Commodity <%=commodityId%></title>
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
    <a href="/commodities">Commodities</a>
    <h3>username: <%=username%></h3>
    <ul>
<%
    for (Commodity commodityObj : store.getCommodities()) {
        if (commodityObj.getId() == commodityId) {
            String tempCategories = "";
            for (int i = 0; i < commodityObj.getCategories().size(); i++) {
                tempCategories += commodityObj.getCategories().get(i);
                if (i < commodityObj.getCategories().size() - 1)
                    tempCategories += ", ";
            }
%>
        <li id="id">Id: <%=commodityObj.getId()%></li>
        <li id="name">Name: <%=commodityObj.getName()%></li>
<%
    for (Provider providerObj : store.getProviders()) {
        if (providerObj.getId() == commodityObj.getProviderId()) {
%>
        <li id="provider">Provider Name: <%=providerObj.getName()%></li>
<%
        }
    }
%>
        <li id="price">Price: <%=commodityObj.getPrice()%></li>
        <li id="categories">Categories: <%=tempCategories%></li>
        <li id="rating">Rating: <%=commodityObj.getRating()%></li>
        <li id="inStock">In Stock: <%=commodityObj.getInStock()%></li>
    </ul>

    <label>Add Your Comment:</label>
    <form action="/comment" method="post">
        <input type="hidden" name="commodityId" value="<%=commodityObj.getId()%>">
        <input type="text" name="commentText" value="" />
        <button type="submit">submit</button>
    </form>
    <br>
    <form action="/rate" method="POST">
        <label>Rate(between 1 and 10):</label>
        <input type="hidden" name="commodityId" value="<%=commodityObj.getId()%>">
        <input type="number" id="score" name="score" min="1" max="10">
        <button type="submit">Rate</button>
    </form>
    <br>
    <form action="/addToBuyList" method="POST">
        <input type="hidden" name="commodityId" value="<%=commodityObj.getId()%>">
        <button type="submit">Add to BuyList</button>
    </form>
    <br>
    <table>
        <caption><h2>Comments</h2></caption>
        <tbody>
        <tr>
            <th>username</th>
            <th>comment</th>
            <th>date</th>
            <th>likes</th>
            <th>dislikes</th>
        </tr>
<%
    for (Comment commentObj : store.getComments()){
        if (commodityObj.getId() == commentObj.getCommodityId()){
            for (User userObj : store.getUsers()){
                if (userObj.getEmail().equalsIgnoreCase(commentObj.getUserEmail())){
%>
        <tr>
            <td><%=userObj.getUsername()%></td>
            <td><%=commentObj.getText()%></td>
            <td><%=commentObj.getDate()%></td>
            <td>
                <form action="/voteComment" method="POST">
                    <%--@declare id=""--%><label for=""><%=commentObj.getLike()%></label>
                    <input type="hidden" name="commodityId" value="<%=commodityObj.getId()%>">
                    <input type="hidden" name="commentId" value="<%=commentObj.getCommentId()%>"/>
                    <button type="submit" name="action" value="like">like</button>
                </form>
            </td>
            <td>
                <form action="/voteComment" method="POST">
                    <%--@declare id=""--%><label for=""><%=commentObj.getDislike()%></label>
                    <input type="hidden" name="commodityId" value="<%=commodityObj.getId()%>">
                    <input type="hidden" name="commentId" value="<%=commentObj.getCommentId()%>"/>
                    <button type="submit" name="action" value="dislike">dislike</button>
                </form>
            </td>
        </tr>
<%
                }
            }
        }
    }
%>
        </tbody>
    </table>
    <br><br>
    <table>
        <caption><h2>Suggested Commodities</h2></caption>
        <tbody>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Provider Id</th>
            <th>Price</th>
            <th>Categories</th>
            <th>Rating</th>
            <th>In Stock</th>
            <th>Links</th>
        </tr>
<%
    ArrayList<Commodity> suggestedCommodities = new ArrayList<Commodity>();
    for (Commodity cmd : store.getCommodities()) {
        if (cmd.getId() == commodityObj.getId())
            continue;
        else
            suggestedCommodities.add(cmd);
    }

    for (Commodity scmd : suggestedCommodities) {
        if (scmd.getCategories().containsAll(commodityObj.getCategories())) {
            scmd.setRating(scmd.getRating() + 11);
        }
    }

    for (int i = 0 ; i < suggestedCommodities.size() - 1 ; i++) {
        for (int j = i+1 ; j < suggestedCommodities.size() ; j++) {
            if (suggestedCommodities.get(i).getRating() < suggestedCommodities.get(j).getRating()) {
                Commodity tempCommodity = suggestedCommodities.get(j);
                suggestedCommodities.set(j, suggestedCommodities.get(i));
                suggestedCommodities.set(i, tempCommodity);
            }
        }
    }
    for (Commodity scmd : suggestedCommodities) {
        if (scmd.getCategories().containsAll(commodityObj.getCategories())) {
            scmd.setRating(scmd.getRating() - 11);
        }
    }
    for (int i = 0 ; i < 5 ; i++) {
%>
        <tr>
            <td><%=suggestedCommodities.get(i).getId()%></td>
            <td><%=suggestedCommodities.get(i).getName()%></td>
        <%
            for (Provider providerObj : store.getProviders()) {
                if (providerObj.getId() == suggestedCommodities.get(i).getProviderId()) {
        %>
            <td>Provider Name: <%=providerObj.getName()%></td>
        <%
                }
            }
        %>
            <td><%=suggestedCommodities.get(i).getPrice()%></td>
            <td><%=tempCategories%></td>
            <td><%=suggestedCommodities.get(i).getRating()%></td>
            <td><%=suggestedCommodities.get(i).getInStock()%></td>
            <td><a href="/commodities/<%=suggestedCommodities.get(i).getId()%>">Link</a></td>
        </tr>
<%
    }
        break;
        }
    }
%>
        </tbody>
    </table>
</body>
</html>
