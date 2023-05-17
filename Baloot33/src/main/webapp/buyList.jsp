<%@ page import="org.example.classes.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Store store = Store.getInstance();
    String username = store.getCurrentUser().getUsername();

    String fixPrice = (String) session.getAttribute("fixedPrice");
    String discountCode = (String) session.getAttribute("discountCode");

    int fixedPrice = Integer.valueOf(fixPrice);

    int discountPrice = -1;

    if (!discountCode.equalsIgnoreCase("#")) {
        float disPrice = 0;

        for (Discount discountObj : store.getDiscounts()) {
            if (discountObj.getDiscountCode().equalsIgnoreCase(discountCode)) {
                disPrice = (fixedPrice) - (fixedPrice * (discountObj.getDiscount() / 100));
            }
        }

        discountPrice = (int) disPrice;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User <%=username%></title>
    <style>
        li {
            padding: 5px
        }
        table {
            width: 100%;
            text-align: center;
            padding: 0 50px;
        }
    </style>
</head>
<body>
    <a href="/">Home</a>
    <%
        for(User userObj : store.getUsers()){
            if(userObj.getUsername().equalsIgnoreCase(username)){
    %>
    <ul>
        <li id="username">Username: <%=userObj.getUsername()%></li>
        <li id="email">Email: <%=userObj.getEmail()%></li>
        <li id="birthDate">Birth Date: <%=userObj.getBirthDate()%></li>
        <li id="address">Address: <%=userObj.getAddress()%></li>
        <li id="credit">Credit: <%=userObj.getCredit()%></li>
        <li id="fixedPrice">Current Buy List Price: <%=fixedPrice%></li>
    <%
        if (!discountCode.equalsIgnoreCase("#")) {
    %>
        <li id="discountPrice">Final Price With Discount: <%=discountPrice%>
            <form action="/clearDiscount" method="POST">
                <button type="submit">Clear Discount</button>
            </form>
        </li>
    <%
        }
    %>
        <li><a href="/credit">Add Credit</a></li>
        <li>
            <form action="/applyDiscount" method="POST">
                <label>Enter Discount Code: </label>
                <input type="hidden" name="fixedPrice" value="<%=fixedPrice%>">
                <input type="text" name="discountCode" value="">
                <button type="submit">Apply</button>
            </form>
        </li>
        <li>
            <form action="/addToPurchasedList" method="POST">
                <label>Submit & Pay</label>
                <input type="hidden" name="credit" value="<%=userObj.getCredit()%>">
                <input type="hidden" name="finalPrice" value="<%
                if (!discountCode.equalsIgnoreCase("#")) {
            %><%=discountPrice%><%
                }
                else {
            %><%=fixedPrice%><%
                }
            %>">
                <input type="hidden" name="discountCode" value="<%=discountCode%>">
                <button type="submit">Payment</button>
            </form>
        </li>
    </ul>
    <%
            }
        }
    %>
    <table>
        <caption>
            <h2>Buy List</h2>
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
            <th></th>
        </tr>
        <%
            for (BuyList buyListObj : store.getBuyLists()) {
                if (buyListObj.getUsername().equalsIgnoreCase(username)) {
                    for (Commodity commodityObj : store.getCommodities()) {
                        if (buyListObj.getCommodityId() == commodityObj.getId()) {
                            int i = 0;
                            String tempCategories = "";
                            for (i = 0; i < commodityObj.getCategories().size(); i++) {
                                tempCategories += commodityObj.getCategories().get(i);
                                if (i < commodityObj.getCategories().size() - 1) {
                                    tempCategories += ", ";
                                }
                            }
        %>
        <tr>
            <td><%=commodityObj.getId()%></td>
            <td><%=commodityObj.getName()%></td>
            <td><%=commodityObj.getProviderId()%></td>
            <td><%=commodityObj.getPrice()%></td>
            <td><%=tempCategories%></td>
            <td><%=commodityObj.getRating()%></td>
            <td><%=commodityObj.getInStock()%></td>
            <td><a href="/commodities/<%=commodityObj.getId()%>">Link</a></td>
            <td>
                <form action="removeFromBuyList" method="POST">
                    <input type="hidden" name="commodityId" value="<%=commodityObj.getId()%>">
                    <button type="submit">Remove</button>
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
    <br>
    <table>
        <caption>
            <h2>Purchased List</h2>
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
            <th></th>
        </tr>
        <%
            for (PurchasedList purchasedListObj : store.getPurchasedLists()) {
                if (purchasedListObj.getUsername().equalsIgnoreCase(username)) {
                    for (Commodity commodityObj : store.getCommodities()) {
                        if (purchasedListObj.getCommodityId() == commodityObj.getId()) {
                            int i = 0;
                            String tempCategories = "";
                            for (i = 0; i < commodityObj.getCategories().size(); i++) {
                                tempCategories += commodityObj.getCategories().get(i);
                                if (i < commodityObj.getCategories().size() - 1) {
                                    tempCategories += ", ";
                                }
                            }
        %>
        <tr>
            <td><%=commodityObj.getId()%></td>
            <td><%=commodityObj.getName()%></td>
            <td><%=commodityObj.getProviderId()%></td>
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
