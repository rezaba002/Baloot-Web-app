package org.example.commands;

import java.util.StringTokenizer;
import org.example.Store;
import org.example.exceptions.Success200Exp;

public class rateCommodity implements Command {
    public String handle(Store store, String secondParam, String thirdParam, String fourthParam) throws Exception {
        if (thirdParam.equalsIgnoreCase("")) {
            StringTokenizer tokenizer1 = new StringTokenizer(secondParam, "=");
            String attribute = tokenizer1.nextToken();
            String usernameContainer = tokenizer1.nextToken();
            StringTokenizer tokenizer2 = new StringTokenizer(usernameContainer, "&");
            String username = tokenizer2.nextToken();
            String idContainer = tokenizer1.nextToken();
            StringTokenizer tokenizer3 = new StringTokenizer(idContainer, "&");
            String id = tokenizer3.nextToken();
            String rate = tokenizer1.nextToken();

            store.rateCommodity(username, Integer.valueOf(id), Integer.valueOf(rate));
            return "commodities/" + id;
        }
        else {
            String username = secondParam;
            int commodityId = Integer.valueOf(thirdParam);
            int score = Integer.valueOf(fourthParam);

            store.rateCommodity(username, commodityId, score);
        }
        return Success200Exp.sendResponse();
    }
}