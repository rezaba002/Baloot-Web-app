package org.example;

import io.javalin.Javalin;

import java.io.IOException;


public class Main {
    public static Javalin Baloot;

    public static Javalin getBaloot() {return Baloot;}

    public static void setBaloot(Javalin baloot) {Baloot = baloot;}

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = new Server();
        Store storeObj = new Store();

        Server.run();
    }
}