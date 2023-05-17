package org.example;

import com.google.gson.Gson;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean keepAsking = true;
        Store store = new Store();
        Gson gson = new Gson();
        while(keepAsking){
            System.out.print("Enter your input: ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("exit")){
                keepAsking = false;
                break;
            }
            if(input.indexOf(' ')==-1){
                if(input.equalsIgnoreCase("getCommoditiesList")){
                    store.getCommoditiesList();
                    continue;
                }
            }
            int spaceIndex = input.indexOf(' ');
            String function = input.substring(0 ,spaceIndex);
            String jSon = input.substring(spaceIndex+1);
            if (function.equalsIgnoreCase("addUser"))
                store.addUser(jSon,gson);
            if (function.equalsIgnoreCase("addProvider"))
                store.addProvider(jSon,gson);
            if (function.equalsIgnoreCase("addCommodity"))
                store.addCommodity(jSon,gson);
            if (function.equalsIgnoreCase("rateCommodity"))
                store.rateCommodity(jSon,gson);
            if (function.equalsIgnoreCase("addToBuyList"))
                store.addToBuyList(jSon,gson);
            if (function.equalsIgnoreCase("removeFromBuyList"))
                store.removeFromBuyList(jSon,gson);
            if (function.equalsIgnoreCase("getCommodityById"))
                store.getCommodityById(jSon,gson);
            if (function.equalsIgnoreCase("getCommoditiesByCategory"))
                store.getCommodityByCategory(jSon,gson);
            if (function.equalsIgnoreCase("getBuyList"))
                store.getBuyList(jSon,gson);
        }
        sc.close();
    }
}