package org.example.commands;

import org.example.BuyList;
import org.example.Commodity;
import org.example.Store;
import org.example.exceptions.NotFound404Exp;
import org.example.User;
import org.example.PurchasedList;
import org.example.exceptions.UserNotFound400Exp;

public class users implements Command {
    public String handle(Store store, String secondParam, String thirdParam, String fourthParam) throws Exception {
        String content= "";

        if (secondParam.equalsIgnoreCase("") || !thirdParam.equalsIgnoreCase("getBuyList") && (!thirdParam.equalsIgnoreCase("") ))
            throw new NotFound404Exp();

        String username = secondParam;

        if (!store.checkIfUserExists(username))
            throw new UserNotFound400Exp();

        else if (thirdParam.equalsIgnoreCase("")) {
            for (User userObj : store.getUsers())
                if(userObj.getUsername().equalsIgnoreCase(username)) {
                    content +=
                            "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <title>User " + username + "</title>\n" +
                            "    <style>\n" +
                            "        li {\n" +
                            "            padding: 5px;\n" +
                            "        }\n" +
                            "        table {\n" +
                            "            width: 100%;\n" +
                            "            text-align: center;\n" +
                            "            padding: 0 50px;\n" +
                            "        }\n" +
                            "        h1 {\n" +
                            "            text-align: center;\n" +
                            "        }\n" +
                            "    </style>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <h1>User: " + username + "</h1>\n" +
                            "    <ul>\n" +
                            "         <li id=\"username\">Username: " + userObj.getUsername() + "</li>\n" +
                            "         <li id=\"email\">Email: " + userObj.getEmail() + "</li>\n" +
                            "         <li id=\"birthDate\">Birth Date: " + userObj.getBirthDate() + "</li>\n" +
                            "         <li id=\"address\">Address: " + userObj.getAddress() + "</li>\n" +
                            "         <li id=\"credit\">Credit: " + userObj.getCredit() + "</li>\n" +
                            "         <li id=\"addCredit\">Add credit: \n" +
                            "               <form style=\"display:inline-block;\" action=\"/addCredit\" method=\"POST\">\n" +
                            "                   <input type=\"hidden\" id=\"username1\" name=\"username1\" value=\"" + userObj.getUsername() +"\" />\n" +
                            "                   <input type=\"number\" id=\"payment\" name=\"payment\" min=\"0\"/>\n" +
                            "                   <button type=\"submit\">addCredit</button>\n" +
                            "               </form>\n" +
                            "         <li id=\"buyListPayment\">Buy List Payment: \n" +
                            "               <form style=\"display:inline-block;\" action=\"/addToPurchasedList\" method=\"POST\">\n" +
                            "                   <input type=\"hidden\" id=\"username2\" name=\"username2\" value=\"" + userObj.getUsername() +"\" />\n" +
                            "                   <button type=\"submit\">Payment</button>\n" +
                            "               </form>\n" +
                            "         </li>\n" +
                            "    </ul>\n" +
                            "    <div>\n"+
                            "    <h1>BuyList</h1>" +
                            "    <table>\n" +
                            "        <tr>\n" +
                            "            <th>Id</th>\n" +
                            "            <th>Name</th>\n" +
                            "            <th>Provider Id</th>\n" +
                            "            <th>Price</th>\n" +
                            "            <th>Categories</th>\n" +
                            "            <th>Rating</th>\n" +
                            "            <th>InStock</th>\n" +
                            "            <th>Links</th>\n" +
                            "            <th></th>\n" +
                            "        </tr>\n";
                            for (BuyList buyListObj : store.getBuyLists()) {
                                if (buyListObj.getUsername().equalsIgnoreCase(secondParam)) {
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
                                                    "            <td>\n" +
                                                    "               <form style=\"display:inline-block;\" action=\"/removeFromBuyList\" method=\"POST\">\n" +
                                                    "               <input type=\"hidden\" id=\"username3\" name=\"username3\" value=\"" + username + "\" />\n" +
                                                    "               <input type=\"hidden\" id=\"commodityId3\" name=\"commodityId3\" value=\"" + commodityObj.getId() + "\" />\n" +
                                                    "               <button type=\"submit\">Remove</button>\n" +
                                                    "               </form>\n" +
                                                    "            </td>\n" +
                                                    "        </tr>\n";
                                        }
                                    }
                        }
                    }
                    content +=
                            "   </table>\n" +
                            "   </div>\n" +
                            "    <br>\n" +
                            "    <h1>Purchased List</h1>" +
                            "    <table>\n" +
                            "        <tr>\n" +
                            "            <th>Id</th>\n" +
                            "            <th>Name</th>\n" +
                            "            <th>Provider Id</th>\n" +
                            "            <th>Price</th>\n" +
                            "            <th>Categories</th>\n" +
                            "            <th>Rating</th>\n" +
                            "            <th>InStock</th>\n" +
                            "        </tr>\n";
                    for (PurchasedList purchasedListObj : store.getPurchasedLists()) {
                        if (purchasedListObj.getUsername().equalsIgnoreCase(secondParam)) {
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
                        }
                    }
                    content+=
                            "    </table>\n"+
                            "    </body>\n" +
                            "</html>";
                }
        }

        else if (!fourthParam.equalsIgnoreCase(""))
            throw new NotFound404Exp();

        else {
            for (User userObj : store.getUsers())
                if(userObj.getUsername().equalsIgnoreCase(username)) {
                    content +=
                            "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <title>User " + username + "'s BuyList</title>\n" +
                            "    <style>\n" +
                            "        li {\n" +
                            "            padding: 5px;\n" +
                            "        }\n" +
                            "        table {\n" +
                            "            width: 100%;\n" +
                            "            text-align: center;\n" +
                            "            padding: 0 50px;\n" +
                            "        }\n" +
                            "        h1 {\n" +
                            "            text-align: center;\n" +
                            "        }\n" +
                            "    </style>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <h1>User: " + username + "'s BuyList</h1>\n" +
                            "    <div>\n"+
                            "    <table>\n" +
                            "        <tr>\n" +
                            "            <th>Id</th>\n" +
                            "            <th>Name</th>\n" +
                            "            <th>Provider Id</th>\n" +
                            "            <th>Price</th>\n" +
                            "            <th>Categories</th>\n" +
                            "            <th>Rating</th>\n" +
                            "            <th>InStock</th>\n" +
                            "            <th>Links</th>\n" +
                            "            <th></th>\n" +
                            "        </tr>\n";
                    for (BuyList buyListObj : store.getBuyLists()) {
                        if (buyListObj.getUsername().equalsIgnoreCase(secondParam)) {
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
                        }
                    }
                    content+=
                            "    </table>\n"+
                            "    </body>\n" +
                            "</html>";
                }
        }
        return content;
    }
}