package org.example.exceptions;

public class NotFound404Exp extends Exception {
    public static String sendResponse() {
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>404 Error</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>" + "404" + "<br>" + "Page Not Found" + "</h1>\n" +
                "</body>\n" +
                "</html>";

        return content;
    }
}