package org.example.commands;

import org.example.Comment;
import org.example.Commodity;
import org.example.Store;
import org.example.User;
import org.example.exceptions.CategoryNotFound400Exp;
import org.example.exceptions.CommodityNotFound400Exp;
import org.example.exceptions.InvalidStartEndPrices400Exp;
import org.example.exceptions.NotFound404Exp;

public class commodities implements Command {
    public String handle(Store store, String secondParam, String thirdParam, String fourthParam) throws Exception {
        String content = "";

        if (secondParam.equalsIgnoreCase("")) {
            content +=
                    "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Commodities</title>\n" +
                    "    <style>\n" +
                    "        table {\n" +
                    "            text-align: center;\n" +
                    "            width: 100%;\n" +
                    "            padding: 0 50px;\n" +
                    "        }\n" +
                    "        h1 {\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <h1>Commodities</h1>\n" +
                    "    <table>\n" +
                    "        <tr>\n" +
                    "            <th>id</th>\n" +
                    "            <th>name</th>\n" +
                    "            <th>providerId</th>\n" +
                    "            <th>price</th>\n" +
                    "            <th>categories</th>\n" +
                    "            <th>rating</th>\n" +
                    "            <th>inStock</th>\n" +
                    "            <th>Links</th>\n" +
                    "        </tr>\n";
            for (Commodity commodityObj : store.getCommodities()) {
                int i = 0;
                String tempCategories = "";
                for (i = 0; i < commodityObj.getCategories().size(); i++) {
                    tempCategories += commodityObj.getCategories().get(i);
                    if (i < commodityObj.getCategories().size() - 1)
                        tempCategories += ", ";
                }
                content +=
                        "        <tr>\n" +
                        "            <td>" + commodityObj.getId() + "</td>\n" +
                        "            <td>" + commodityObj.getName() + "</td>\n" +
                        "            <td>" + commodityObj.getProviderId() + "</td>\n" +
                        "            <td>" + commodityObj.getPrice() + "</td>\n" +
                        "            <td>" + tempCategories + "</td>\n" +
                        "            <td>" + commodityObj.getRating() + "</td>\n" +
                        "            <td>" + commodityObj.getInStock() + "</td>\n" +
                        "            <td><a href=\"/commodities/" + commodityObj.getId() + "\">Link</a></td>\n" +
                        "        </tr>\n";
            }
            content +=
                    "    </table>\n" +
                    "</body>\n" +
                    "</html>";

            return content;
        }

        else if (thirdParam.equalsIgnoreCase("")) {

            int commodityId = Integer.valueOf(secondParam);

            if (!store.checkIfCommodityExists(commodityId))
                throw new CommodityNotFound400Exp();

            for (Commodity commodityObj : store.getCommodities()) {
                if (commodityObj.getId() == Integer.parseInt(secondParam)) {
                    String tempCategories = "";
                    for (int i = 0; i < commodityObj.getCategories().size(); i++) {
                        tempCategories += commodityObj.getCategories().get(i);
                        if (i < commodityObj.getCategories().size() - 1)
                            tempCategories += ", ";
                    }
                    content +=
                            "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <title>Commodity " + commodityId + "</title>\n" +
                            "    <style>\n" +
                            "        li {\n" +
                            "            padding: 5px;\n" +
                            "        }\n" +
                            "        table {\n" +
                            "            width: 80%;\n" +
                            "            text-align: center;\n" +
                            "        }\n" +
                            "        h1 {\n" +
                            "            text-align: center;\n" +
                            "        }\n" +
                            "    </style>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <h1>Commodity with Id: " + commodityId + "</h1>\n" +
                            "    <ul>\n" +
                            "         <li>Id: " + commodityObj.getId() + "</li>\n" +
                            "         <li>Name: " + commodityObj.getName() + "</li>\n" +
                            "         <li>Provider Id: " + commodityObj.getProviderId() + "</li>\n" +
                            "         <li>Price: " + commodityObj.getPrice() + "</li>\n" +
                            "         <li>Categories: " + tempCategories + "</li>\n" +
                            "         <li>Rating: " + commodityObj.getRating() + "</li>\n" +
                            "         <li>In Stock: " + commodityObj.getInStock() + "</li>\n" +
                            "    </ul>\n" +
                            "    <br><br>\n" +
                            "    <form action=\"/rateCommodity\"method=\"POST\">\n" +
                            "        <label>Your Username:</label>\n" +
                            "        <input type=\"text\" id=\"username1\" name=\"username1\" value=\"\" />\n" +
                            "        <input type=\"hidden\" id=\"id1\" name=\"id1\" value=\"" + commodityObj.getId() +"\">\n" +
                            "        <label>Rate(between 1 and 10):</label>\n" +
                            "        <input type=\"number\" id=\"score\" name=\"score\" min=\"1\" max=\"10\">\n" +
                            "        <button type=\"submit\">Rate</button>\n" +
                            "    </form>\n" +
                            "    <br>\n" +
                            "    <form action=\"/addToBuyList\" method=\"POST\">\n" +
                            "        <label>Your Username:</label>\n" +
                            "        <input type=\"text\" id=\"username2\" name=\"username2\" value=\"\" />\n" +
                            "        <input type=\"hidden\" id=\"id2\" name=\"id2\" value=\"" + commodityObj.getId() + "\" />\n" +
                            "        <button type=\"submit\">Add to BuyList</button>\n" +
                            "    </form>\n" +
                            "    <br/>\n" +
                            "    <table>\n" +
                            "        <tr>\n" +
                            "            <th>username</th>\n" +
                            "            <th>comment</th>\n" +
                            "            <th>date</th>\n" +
                            "            <th></th>\n" +
                            "            <th></th>\n" +
                            "        </tr>\n";

                    for (Comment commentObj : store.getComments()){
                        if (commodityObj.getId() == commentObj.getCommodityId()){
                            for (User userObj : store.getUsers()){
                                if (userObj.getEmail().equalsIgnoreCase(commentObj.getUserEmail())){
                                    content +=
                                            "        <tr>\n" +
                                            "            <td>" + userObj.getUsername() + "</td>\n" +
                                            "            <td>" + commentObj.getText() + "</td>\n" +
                                            "            <td>" + commentObj.getDate() + "</td>\n" +
                                            "            <td>\n" +
                                            "                <form style=\"display:inline-block;\" action=\"/likeComment\" method=\"POST\">\n" +
                                            "                    <label for=\"\">" + commentObj.getLike() + "</label>\n" +
                                            "                    <input type=\"hidden\" id=\"commodityId1\" name=\"commodityId1\" value=\"" + commodityObj.getId() + "\"/>\n" +
                                            "                    <input type=\"hidden\" id=\"commendId1\" name=\"commentId1\" value=\"" + commentObj.getCommentId() + "\"/>\n" +
                                            "                    <button type=\"submit\">like</button>\n" +
                                            "                </form>\n" +
                                            "            </td>\n" +
                                            "            <td>\n" +
                                            "                <form style=\"display:inline-block;\" action=\"/dislikeComment\" method=\"POST\">\n" +
                                            "                    <label for=\"\">" + commentObj.getDislike() + "</label>\n" +
                                            "                    <input type=\"hidden\" id=\"commodityId2\" name=\"commodityId2\" value=\"" + commodityObj.getId() + "\"/>\n" +
                                            "                    <input type=\"hidden\" id=\"commendId2\" name=\"commentId2\" value=\"" + commentObj.getCommentId() + "\"/>\n" +
                                            "                    <button type=\"submit\">dislike</button>\n" +
                                            "                </form>\n" +
                                            "            </td>\n" +
                                            "        </tr>\n";
                                }
                            }
                        }
                    }
                    content +=
                            "    </table>\n" +
                            "</body>\n" +
                            "</html>\n";
                }
            }
        }

        else if (fourthParam.equalsIgnoreCase("")) {

            if (!secondParam.equalsIgnoreCase("search"))
                throw new NotFound404Exp();

            String desiredCategory = thirdParam;

            if (!store.checkIfCategoryExists(desiredCategory))
                throw new CategoryNotFound400Exp();

            content +=
                    "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <title>Category: " + desiredCategory + "</title>\n" +
                            "    <style>\n" +
                            "        table {\n" +
                            "            text-align: center;\n" +
                            "            width: 100%;\n" +
                            "            padding: 0 50px;\n" +
                            "        }\n" +
                            "        h1 {\n" +
                            "            text-align: center;\n" +
                            "        }\n" +
                            "    </style>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <h1>Commodities in Category: " + desiredCategory + "</h1>\n" +
                            "    <table>\n" +
                            "        <tr>\n" +
                            "            <th>id</th>\n" +
                            "            <th>name</th>\n" +
                            "            <th>providerId</th>\n" +
                            "            <th>price</th>\n" +
                            "            <th>categories</th>\n" +
                            "            <th>rating</th>\n" +
                            "            <th>inStock</th>\n" +
                            "            <th>Links</th>\n" +
                            "        </tr>\n";

            for (Commodity commodityObj : store.getCommodities()) {
                if (commodityObj.getCategories().contains(desiredCategory)) {
                    int i = 0;
                    String tempCategories = "";
                    for (i = 0; i < commodityObj.getCategories().size(); i++) {
                        tempCategories += commodityObj.getCategories().get(i);
                        if (i < commodityObj.getCategories().size() - 1)
                            tempCategories += ", ";
                    }
                    content +=
                            "        <tr>\n" +
                            "            <td>" + commodityObj.getId() + "</td>\n" +
                            "            <td>" + commodityObj.getName() + "</td>\n" +
                            "            <td>" + commodityObj.getProviderId() + "</td>\n" +
                            "            <td>" + commodityObj.getPrice() + "</td>\n" +
                            "            <td>" + tempCategories + "</td>\n" +
                            "            <td>" + commodityObj.getRating() + "</td>\n" +
                            "            <td>" + commodityObj.getInStock() + "</td>\n" +
                            "            <td><a href=\"/commodities/" + commodityObj.getId() + "\">Link</a></td>\n" +
                            "        </tr>\n";
                }
            }
            content +=
                    "    </table>\n" +
                            "</body>\n" +
                            "</html>";

            return content;
        }

        else {

            if (!secondParam.equalsIgnoreCase("search"))
                throw new NotFound404Exp();

            int startPrice = Integer.valueOf(thirdParam);
            int endPrice = Integer.valueOf(fourthParam);

            if (startPrice > endPrice)
                throw new InvalidStartEndPrices400Exp();

            content +=
                    "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <title>Price Filter = [ " + startPrice + " , " + endPrice + " ]</title>\n" +
                            "    <style>\n" +
                            "        table {\n" +
                            "            text-align: center;\n" +
                            "            width: 100%;\n" +
                            "            padding: 0 50px;\n" +
                            "        }\n" +
                            "        h1 {\n" +
                            "            text-align: center;\n" +
                            "        }\n" +
                            "    </style>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <h1>Price Filter = [ " + startPrice + " , " + endPrice + " ]</h1>\n" +
                            "    <table>\n" +
                            "        <tr>\n" +
                            "            <th>id</th>\n" +
                            "            <th>name</th>\n" +
                            "            <th>providerId</th>\n" +
                            "            <th>price</th>\n" +
                            "            <th>categories</th>\n" +
                            "            <th>rating</th>\n" +
                            "            <th>inStock</th>\n" +
                            "            <th>Links</th>\n" +
                            "        </tr>\n";

            for (Commodity commodityObj : store.getCommodities()) {
                if (commodityObj.getPrice() >= startPrice && commodityObj.getPrice() <= endPrice) {
                    int i = 0;
                    String tempCategories = "";
                    for (i = 0; i < commodityObj.getCategories().size(); i++) {
                        tempCategories += commodityObj.getCategories().get(i);
                        if (i < commodityObj.getCategories().size() - 1)
                            tempCategories += ", ";
                    }
                    content +=
                            "        <tr>\n" +
                                    "            <td>" + commodityObj.getId() + "</td>\n" +
                                    "            <td>" + commodityObj.getName() + "</td>\n" +
                                    "            <td>" + commodityObj.getProviderId() + "</td>\n" +
                                    "            <td>" + commodityObj.getPrice() + "</td>\n" +
                                    "            <td>" + tempCategories + "</td>\n" +
                                    "            <td>" + commodityObj.getRating() + "</td>\n" +
                                    "            <td>" + commodityObj.getInStock() + "</td>\n" +
                                    "            <td><a href=\"/commodities/" + commodityObj.getId() + "\">Link</a></td>\n" +
                                    "        </tr>\n";
                }
            }
            content +=
                    "    </table>\n" +
                            "</body>\n" +
                            "</html>";

            return content;
        }
        return content;
    }
}
