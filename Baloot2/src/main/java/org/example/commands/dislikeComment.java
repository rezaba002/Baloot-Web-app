package org.example.commands;

import org.example.Comment;
import org.example.Store;

import java.util.StringTokenizer;

public class dislikeComment implements Command {
    public String handle(Store store, String secondParam, String thirdParam, String fourthParam) throws Exception {
        StringTokenizer tokenizer1 = new StringTokenizer(secondParam,"=");
        String attribute = tokenizer1.nextToken();
        String commodityIdContainer = tokenizer1.nextToken();
        StringTokenizer tokenizer2 = new StringTokenizer(commodityIdContainer,"&");
        String commodityId = tokenizer2.nextToken();
        int commentId = Integer.valueOf(tokenizer1.nextToken());

        store.voteComment("clientUser", commentId, -1);

        return "commodities/" + commodityId;
    }
}