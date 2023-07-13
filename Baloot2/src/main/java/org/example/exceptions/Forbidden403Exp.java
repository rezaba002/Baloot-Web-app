package org.example.exceptions;

public class Forbidden403Exp extends Exception {
    public static String sendResponse() {
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>403 Forbidden</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>" + "403" + "<br>" + "This function is Forbidden" + "</h1>\n" +
                "</body>\n" +
                "</html>";

        return content;
    }
}