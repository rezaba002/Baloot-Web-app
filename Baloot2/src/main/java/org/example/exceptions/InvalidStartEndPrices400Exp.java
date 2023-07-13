package org.example.exceptions;

public class InvalidStartEndPrices400Exp extends Exception {
    public static String sendResponse() {
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>400 Invalid Start & End Prices</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>" + "400" + "<br>" + "Start Price is larger than End Price!" + "</h1>\n" +
                "</body>\n" +
                "</html>";

        return content;
    }
}