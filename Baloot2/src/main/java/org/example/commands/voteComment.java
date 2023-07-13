package org.example.commands;

import org.example.Store;
import org.example.exceptions.Success200Exp;

public class voteComment implements Command {
    public String handle(Store store, String secondParam, String thirdParam, String fourthParam) throws Exception {

        String username = secondParam;
        int commentId = Integer.valueOf(thirdParam);
        int vote = Integer.valueOf(fourthParam);

        store.voteComment(username, commentId, vote);

        return Success200Exp.sendResponse();
    }
}