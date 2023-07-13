package org.example.commands;

import org.example.Store;

public interface Command {
    public String handle(Store store, String secondParam, String thirdParam, String fourthParam) throws Exception;
}