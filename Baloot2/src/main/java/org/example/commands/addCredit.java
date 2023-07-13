package org.example.commands;

import org.example.Store;
import org.example.User;
import org.example.exceptions.InvalidCredit400Exp;
import org.example.exceptions.NotFound404Exp;
import org.example.exceptions.Success200Exp;
import org.example.exceptions.UserNotFound400Exp;

import java.util.StringTokenizer;

public class addCredit implements Command {
    public String handle(Store store, String secondParam, String thirdParam, String fourthParam) throws Exception {
        if (thirdParam.equalsIgnoreCase("")) {
            StringTokenizer tokenizer1 = new StringTokenizer(secondParam, "=");
            String attribute = tokenizer1.nextToken();
            String usernameContainer = tokenizer1.nextToken();
            StringTokenizer tokenizer2 = new StringTokenizer(usernameContainer, "&");
            String username = tokenizer2.nextToken();
            int addingCredit = Integer.valueOf(tokenizer1.nextToken());
            for (User userObj : store.getUsers()) {
                if (username.equalsIgnoreCase(userObj.getUsername())) {
                    userObj.setCredit(userObj.getCredit() + addingCredit);
                }
            }
            return "users/" + username;
        }

        else {
            if (!fourthParam.equalsIgnoreCase(""))
                throw new NotFound404Exp();

            String username = secondParam;

            if (!store.checkIfUserExists(username))
                throw new UserNotFound400Exp();

            int addingCredit = Integer.valueOf(thirdParam);

            if (!store.checkIfCreditIsPositiveNumber(addingCredit))
                throw new InvalidCredit400Exp();

            for (User userObj : store.getUsers()) {
                if (username.equalsIgnoreCase(userObj.getUsername())) {
                    userObj.setCredit(userObj.getCredit() + addingCredit);
                }
            }
        }
        return Success200Exp.sendResponse();
    }
}