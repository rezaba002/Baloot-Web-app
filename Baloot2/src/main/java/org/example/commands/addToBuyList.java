package org.example.commands;

import org.example.Store;
import org.example.exceptions.*;

import java.util.StringTokenizer;

public class addToBuyList implements Command {
    public String handle(Store store, String secondParam, String thirdParam, String fourthParam) throws Exception {
        if (thirdParam.equalsIgnoreCase("")) {
            StringTokenizer tokenizer1 = new StringTokenizer(secondParam, "=");
            String attribute = tokenizer1.nextToken();
            String usernameContainer = tokenizer1.nextToken();
            StringTokenizer tokenizer2 = new StringTokenizer(usernameContainer, "&");
            String username = tokenizer2.nextToken();
            String commodityId = tokenizer1.nextToken();
            store.addToBuyList(username, Integer.valueOf(commodityId));
            return "commodities/" + commodityId;
        }
        else {
            if (!fourthParam.equalsIgnoreCase(""))
                throw new NotFound404Exp();

            String username = secondParam;
            int commodityId = Integer.valueOf(thirdParam);

            store.addToBuyList(username, commodityId);
        }
        return Success200Exp.sendResponse();
    }
}