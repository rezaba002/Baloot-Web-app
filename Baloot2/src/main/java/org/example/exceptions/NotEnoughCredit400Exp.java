package org.example.exceptions;

public class NotEnoughCredit400Exp extends Exception {
    public static String sendResponse() {
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>400 Not Enough Credit</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>" + "400" + "<br>" + "Not Enough Credit!" + "</h1>\n" +
                "</body>\n" +
                "</html>";

        return content;
    }
}