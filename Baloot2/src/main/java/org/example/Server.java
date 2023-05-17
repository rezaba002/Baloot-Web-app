package org.example;

import com.google.gson.Gson;
import io.javalin.Javalin;
import org.example.commands.Command;
import org.example.exceptions.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.StringTokenizer;

public class Server {
    public static Javalin Baloot;


//----------------------------------------------------------------------------------------------------------------------
//                                                    API URLs
//----------------------------------------------------------------------------------------------------------------------

    public static final String Users_API_URL = "http://5.253.25.110:5000/api/users";
    public static final String Providers_API_URL = "http://5.253.25.110:5000/api/providers";
    public static final String Commodities_API_URL = "http://5.253.25.110:5000/api/commodities";
    public static final String Comments_API_URL = "http://5.253.25.110:5000/api/comments";



//----------------------------------------------------------------------------------------------------------------------
//                                               Download API Data
//----------------------------------------------------------------------------------------------------------------------


    public static void run() throws IOException, InterruptedException {
        Store storeObj = new Store();
        Gson gsonObj = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestUsers = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/Json")
                .uri(URI.create(Users_API_URL))
                .build();
        storeObj.addUser(client, requestUsers, gsonObj);
        System.out.println("----------------------------------------------------");
        System.out.println("        Users API successfully downloaded");
        System.out.println("----------------------------------------------------");
        HttpRequest requestProviders = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/Json")
                .uri(URI.create(Providers_API_URL))
                .build();
        storeObj.addProvider(client, requestProviders, gsonObj);
        System.out.println("----------------------------------------------------");
        System.out.println("       Providers API successfully downloaded");
        System.out.println("----------------------------------------------------");
        HttpRequest requestCommodities = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/Json")
                .uri(URI.create(Commodities_API_URL))
                .build();
        storeObj.addCommodity(client, requestCommodities, gsonObj);
        System.out.println("----------------------------------------------------");
        System.out.println("      Commodities API successfully downloaded");
        System.out.println("----------------------------------------------------");
        HttpRequest requestComments = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/Json")
                .uri(URI.create(Comments_API_URL))
                .build();
        storeObj.addComment(client, requestComments, gsonObj);
        System.out.println("----------------------------------------------------");
        System.out.println("       Comments API successfully downloaded");
        System.out.println("----------------------------------------------------");


//----------------------------------------------------------------------------------------------------------------------
//                                                  Start Server
//----------------------------------------------------------------------------------------------------------------------


        Baloot = Javalin.create().start(5000);


//----------------------------------------------------------------------------------------------------------------------
//                                                      GET
//----------------------------------------------------------------------------------------------------------------------


        Baloot.get("*",
                ctx -> {
                    StringTokenizer tokenizer = new StringTokenizer(ctx.url(), "/");
                    String httpString = tokenizer.nextToken();
                    String domainString = tokenizer.nextToken();
                    String firstToken = "", secondToken = "", thirdToken = "", fourthToken = "", response = "";
                    if (tokenizer.hasMoreElements())
                        firstToken = tokenizer.nextToken();
                    if (tokenizer.hasMoreElements())
                        secondToken = tokenizer.nextToken();
                    if (tokenizer.hasMoreElements())
                        thirdToken = tokenizer.nextToken();
                    if (tokenizer.hasMoreElements())
                        fourthToken = tokenizer.nextToken();
                    if (tokenizer.hasMoreElements()) {
                        ctx.status(404);
                        ctx.contentType("text/html");
                        ctx.result(NotFound404Exp.sendResponse());
                        return;
                    }
                    if (firstToken.equalsIgnoreCase("")){
                        ctx.contentType("text/html");
                        ctx.result(HomePage.sendResponse());
                        return;
                    }
                    try {
                        Class<Command> commandClass = (Class<Command>) Class.forName("org.example.commands." + firstToken);
                        Command newCommand = commandClass.getDeclaredConstructor().newInstance();
                        response = newCommand.handle(storeObj, secondToken, thirdToken, fourthToken);
                    }
                    catch (Exception exp){
                        if (exp instanceof AlreadyInBuyList400Exp) {
                            response = AlreadyInBuyList400Exp.sendResponse();
                            ctx.status(400);
                        }
                        else if (exp instanceof CategoryNotFound400Exp) {
                            response = CategoryNotFound400Exp.sendResponse();
                            ctx.status(400);
                        }
                        else if (exp instanceof CommentNotFound400Exp) {
                            response = CommentNotFound400Exp.sendResponse();
                            ctx.status(400);
                        }
                        else if (exp instanceof CommodityNotFound400Exp) {
                            response = CommodityNotFound400Exp.sendResponse();
                            ctx.status(400);
                        }
                        else if (exp instanceof Forbidden403Exp) {
                            response = Forbidden403Exp.sendResponse();
                            ctx.status(403);
                        }
                        else if (exp instanceof InvalidCredit400Exp) {
                            response = InvalidCredit400Exp.sendResponse();
                            ctx.status(400);
                        }
                        else if (exp instanceof InvalidScore400Exp) {
                            response = InvalidScore400Exp.sendResponse();
                            ctx.status(400);
                        }
                        else if (exp instanceof InvalidStartEndPrices400Exp) {
                            response = InvalidStartEndPrices400Exp.sendResponse();
                            ctx.status(400);
                        }
                        else if (exp instanceof InvalidVote400Exp) {
                            response = InvalidVote400Exp.sendResponse();
                            ctx.status(400);
                        }
                        else if (exp instanceof NotFound404Exp || exp instanceof ClassNotFoundException) {
                            response = NotFound404Exp.sendResponse();
                            ctx.status(404);
                        }
                        else if (exp instanceof NotFoundInBuyList400Exp) {
                            response = NotFoundInBuyList400Exp.sendResponse();
                            ctx.status(400);
                        }
                        else if (exp instanceof OutOfStock400Exp) {
                            response = OutOfStock400Exp.sendResponse();
                            ctx.status(400);
                        }
                        else if (exp instanceof ProviderNotFound400Exp) {
                            response = ProviderNotFound400Exp.sendResponse();
                            ctx.status(400);
                        }
                        else if (exp instanceof UserNotFound400Exp) {
                            response = UserNotFound400Exp.sendResponse();
                            ctx.status(400);
                        }
                    }
                    ctx.contentType("text/html");
                    ctx.result(response);
                });



//----------------------------------------------------------------------------------------------------------------------
//                                                      POST
//----------------------------------------------------------------------------------------------------------------------


        Baloot.post("*",
                ctx -> {
                    boolean noRedirect = false;
                    StringTokenizer tokenizer = new StringTokenizer(ctx.url(), "/");
                    String httpString = tokenizer.nextToken();
                    String domainString = tokenizer.nextToken();
                    String firstToken = "", redirectUrl = "";
                    if (tokenizer.hasMoreElements())
                        firstToken = tokenizer.nextToken();

                    Class<Command> commandClass = (Class<Command>) Class.forName("org.example.commands." + firstToken);
                    Command newCommand = commandClass.getDeclaredConstructor().newInstance();

                    try {
                        redirectUrl = newCommand.handle(storeObj, ctx.body(),"","");
                    }
                    catch (Exception exp) {
                        if (exp instanceof AlreadyInBuyList400Exp) {
                            ctx.status(400);
                            ctx.contentType("text/html");
                            ctx.result(AlreadyInBuyList400Exp.sendResponse());
                            noRedirect = true;
                        }
                        else if (exp instanceof NotEnoughCredit400Exp) {
                            ctx.status(400);
                            ctx.contentType("text/html");
                            ctx.result(NotEnoughCredit400Exp.sendResponse());
                            noRedirect = true;
                        }
                        else if (exp instanceof OutOfStock400Exp) {
                            ctx.status(400);
                            ctx.contentType("text/html");
                            ctx.result(OutOfStock400Exp.sendResponse());
                            noRedirect = true;
                        }
                        else if (exp instanceof UserNotFound400Exp) {
                            ctx.status(400);
                            ctx.contentType("text/html");
                            ctx.result(UserNotFound400Exp.sendResponse());
                            noRedirect = true;
                        }
                    }
                    if (!noRedirect)
                        ctx.redirect(redirectUrl);
                });
    }
}

