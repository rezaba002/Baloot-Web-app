package org.example.exceptions;

public class InvalidScore400Exp extends Exception {
    public static String sendResponse() {
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>400 Invalid Score</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>" + "400" + "<br>" + "Invalid Score Value!" + "</h1>\n" +
                "</body>\n" +
                "</html>";

        return content;
    }
}
