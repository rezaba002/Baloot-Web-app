package org.example.exceptions;

public class AlreadyInBuyList400Exp extends Exception {
    public static String sendResponse() {
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>400 Already In BuyList</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>" + "400" + "<br>" + "Already In User's BuyList!" + "</h1>\n" +
                "</body>\n" +
                "</html>";

        return content;
    }
}