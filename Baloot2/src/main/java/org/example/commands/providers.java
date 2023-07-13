package org.example.commands;

import org.example.Commodity;
import org.example.Provider;
import org.example.Store;
import org.example.exceptions.NotFound404Exp;
import org.example.exceptions.ProviderNotFound400Exp;

public class providers implements Command {
    public String handle(Store store, String secondParam, String thirdParam, String fourthParam) throws Exception {
        String content="";

        if (secondParam.equalsIgnoreCase("") || !thirdParam.equalsIgnoreCase(""))
            throw new NotFound404Exp();

        int providerId = Integer.valueOf(secondParam);

        if (!store.checkIfProviderExists(providerId))
            throw new ProviderNotFound400Exp();

        else {
            for (Provider providerObj : store.getProviders()) {
                if (providerObj.getId() == providerId) {
                    content +=
                            "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <title>Provider " + providerId + "</title>\n" +
                            "    <style>\n" +
                            "        li {\n" +
                            "            padding: 5px;\n" +
                            "        }\n" +
                            "        table {\n" +
                            "            width: 100%;\n" +
                            "            text-align: center;\n" +
                            "            padding: 0 50px;\n" +
                            "        }\n" +
                            "        h3, h1 {\n" +
                            "            text-align: center;\n" +
                            "        }\n" +
                            "    </style>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <h1>Provider with Id: " + providerId + "</h1>\n" +
                            "    <ul>\n" +
                            "         <li id=\"id\">Id: " + providerObj.getId() + "</li>\n" +
                            "         <li id=\"name\">Name: " + providerObj.getName() + "</li>\n" +
                            "         <li id=\"registerDate\">Register Date: " + providerObj.getRegistryDate() + "</li>\n" +
                            "    </ul>\n" +
                            "    <h3>Provided Commodities</h3>" +
                            "    <table>\n" +
                            "        <tr>\n" +
                            "            <th>Id</th>\n" +
                            "            <th>Name</th>\n" +
                            "            <th>Price</th>\n" +
                            "            <th>Categories</th>\n" +
                            "            <th>Rating</th>\n" +
                            "            <th>In Stock</th>\n" +
                            "            <th>Links</th>\n" +
                            "        </tr>\n";
                            for (Commodity commodityObj : store.getCommodities()) {
                                if (commodityObj.getProviderId() == Integer.valueOf(secondParam)) {
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
                                            "            <td>" + commodityObj.getPrice() + "</td>\n" +
                                            "            <td>" + tempCategories + "</td>\n" +
                                            "            <td>" + commodityObj.getRating() + "</td>\n" +
                                            "            <td>" + commodityObj.getInStock() + "</td>\n" +
                                            "            <td><a href=\"/commodities/" + commodityObj.getId() + "\">Link</a></td>\n" +
                                            "        </tr>\n";
                                }
                            }
                            content +=
                                    "       </table>\n"+
                                    "    </body>\n" +
                                    "</html>";
                }
            }
        }
        return content;
    }
}
