package org.example.exceptions;

public class HomePage {
    public static String sendResponse(){
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Homepage</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1 style=\"text-align:center;\"> Welcome To Baloot </h1>\n" +
                "</body>\n" +
                "</html>";
        return content;
    }
}
