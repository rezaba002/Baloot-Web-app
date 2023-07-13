package org.example.exceptions;

public class CommodityNotFound400Exp extends Exception {
    public static String sendResponse() {
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>400 Commodity Not Found</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>" + "400" + "<br>" + "Commodity Not Found!" + "</h1>\n" +
                "</body>\n" +
                "</html>";

        return content;
    }
}