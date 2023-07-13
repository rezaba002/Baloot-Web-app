package org.example.exceptions;

public class NotFoundInBuyList400Exp extends Exception {
    public static String sendResponse() {
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>400 Not Found In BuyList</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>" + "400" + "<br>" + "Not Found In BuyList!" + "</h1>\n" +
                "</body>\n" +
                "</html>";

        return content;
    }
}