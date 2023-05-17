package org.example.exceptions;

public class Success200Exp extends Exception {
    public static String sendResponse() {
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>200 Success</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>" + "200" + "<br>" + "Function Done Successfully!" + "</h1>\n" +
                "</body>\n" +
                "</html>";

        return content;
    }
}
