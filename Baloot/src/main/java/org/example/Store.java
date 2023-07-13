package org.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Store {
    OutputFail outputFail = new OutputFail();
    OutputSuccess outputSuccess = new OutputSuccess();


    private ArrayList<User> users = new ArrayList<User>();
    public ArrayList<User> getUsers() {return users;}
    public void setUsers(ArrayList<User> users) {this.users = users;}

    private ArrayList<Provider> providers = new ArrayList<Provider>();
    public ArrayList<Provider> getProviders() {return providers;}
    public void setProviders(ArrayList<Provider> providers) {this.providers = providers;}

    private ArrayList<Commodity> commodities = new ArrayList<Commodity>();
    public ArrayList<Commodity> getCommodities() {return commodities;}
    public void setCommodities(ArrayList<Commodity> commodities) {this.commodities = commodities;}

    private ArrayList<RateCommodity> rateCommodities = new ArrayList<RateCommodity>();
    private ArrayList<BuyList> buyLists = new ArrayList<BuyList>();

    public ArrayList<BuyList> getBuyLists() {return buyLists;}

    public void setBuyLists(ArrayList<BuyList> buyLists) {this.buyLists = buyLists;}

    public ArrayList<RateCommodity> getRateCommodities() {return rateCommodities;}

    public void setRateCommodities(ArrayList<RateCommodity> rateCommodities) {this.rateCommodities = rateCommodities;}

    public void addUser(String jSon, Gson gson) {
        User user = gson.fromJson(jSon, User.class);
        //errors
        if (!usernameValidation(user.getUsername())) {
            outputFail.setSuccess(false);
            outputFail.setData("Username invalid!");
            System.out.println(gson.toJson(outputFail));
            return;
        }
        //update user
        for (int i = 0; i < users.size(); i++) {
            if (user.getUsername().equalsIgnoreCase(users.get(i).getUsername())) {
                users.set(i, user);
                return;
            }
        }
        //add new user
        users.add(user);
    }

    public void addProvider(String jSon, Gson gson) {
        Provider provider = gson.fromJson(jSon, Provider.class);
        //errors
        if (!providerValidation(provider)) {
            outputFail.setSuccess(false);
            outputFail.setData("This provider is not valid");
            System.out.println(gson.toJson(outputFail));
            return;
        }
        //add new provider
        providers.add(provider);
    }

    public void addCommodity(String jSon, Gson gson) {
        Commodity commodity = gson.fromJson(jSon, Commodity.class);
        //errors
        for (Commodity com : commodities) {
            if (com.getId() == commodity.getId()) {
                outputFail.setSuccess(false);
                outputFail.setData("This id is already defined");
                System.out.println(gson.toJson(outputFail));
                return;
            }
        }
        //add new commodity
        boolean x = false;
        for (Provider prv : providers) {
            if (commodity.getProviderId() == prv.getId()) {
                x = true;
            }
        }
        if (x) {
            commodities.add(commodity);
            commodity.setNumberOfVoters(commodity.getNumberOfVoters() + 1);
        }
        //errors
        else {
            outputFail.setSuccess(false);
            outputFail.setData("No provider exists with this id number!");
            System.out.println(gson.toJson(outputFail));
        }
    }

    public void getCommoditiesList() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        Gson gson = new Gson();
        for (Commodity com : commodities) {
            jsonArray.add(JsonParser.parseString(gson.toJson(com)).getAsJsonObject());
        }
        jsonObject.add("commoditiesList", jsonArray);
        outputSuccess.setSuccess(true);
        outputSuccess.setData(jsonObject);
        System.out.println(gson.toJson(outputSuccess));
    }

    public void rateCommodity(String jSon, Gson gson) {
        RateCommodity rateCommodity = gson.fromJson(jSon, RateCommodity.class);
        //errors
        boolean x = false;
        boolean y = false;
        for (User usr : users) {
            if (rateCommodity.getUsername().equalsIgnoreCase(usr.getUsername())) {
                x = true;
            }
        }
        if (!x) {
            outputFail.setSuccess(false);
            outputFail.setData("No user exists with this username");
            System.out.println(gson.toJson(outputFail));
            return;
        }
        for (Commodity cmd : commodities) {
            if (rateCommodity.getCommodityId() == cmd.getId()) {
                y = true;
            }
        }
        if (!y) {
            outputFail.setSuccess(false);
            outputFail.setData("No commodity exists with this commodityId");
            System.out.println(gson.toJson(outputFail));
            return;
        }
        if (rateCommodity.getScore() > 10 || rateCommodity.getScore() < 1) {
            outputFail.setSuccess(false);
            outputFail.setData("Score is not in defined range : [1,10]");
            System.out.println(gson.toJson(outputFail));
            return;
        }
        //rate
        if (x && y) {
            int i = 0;
            for (Commodity cmd : commodities) {
                //update score of rating
                for (int j = 0; j < rateCommodities.size(); j++) {
                    if (rateCommodity.getUsername().equalsIgnoreCase(rateCommodities.get(j).getUsername()) && rateCommodity.getCommodityId() == cmd.getId()) {
                        commodities.get(i).setRating((float) ((commodities.get(i).getRating() * commodities.get(i).getNumberOfVoters()) - rateCommodities.get(j).getScore()) / (commodities.get(i).getNumberOfVoters() - 1));
                        rateCommodities.set(j, rateCommodity);
                        commodities.get(i).setRating((float) (rateCommodity.getScore() + (commodities.get(i).getRating() * (commodities.get(i).getNumberOfVoters() - 1))) / commodities.get(i).getNumberOfVoters());
                        return;
                    }
                }
                //add score to rating
                rateCommodities.add(rateCommodity);
                if (rateCommodity.getCommodityId() == cmd.getId()) {
                    commodities.get(i).setRating((float) (rateCommodity.getScore() + (commodities.get(i).getRating() * commodities.get(i).getNumberOfVoters())) / (commodities.get(i).getNumberOfVoters() + 1));
                    commodities.get(i).setNumberOfVoters(commodities.get(i).getNumberOfVoters() + 1);
                }
                i++;
            }
        }
    }

    public void addToBuyList(String jSon, Gson gson) {
        BuyList buyList = gson.fromJson(jSon, BuyList.class);
        //errors
        if (commodityDoesntExists(buyList.getCommodityId())) {
            outputFail.setSuccess(false);
            outputFail.setData("No commodity exists with this commodityId");
            System.out.println(gson.toJson(outputFail));
            return;
        }
        if (!isCommodityAvailable(buyList.getCommodityId())) {
            outputFail.setSuccess(false);
            outputFail.setData("This commodity is out of stock");
            System.out.println(gson.toJson(outputFail));
            return;
        }
        if (!userExists(buyList.getUsername())) {
            outputFail.setData("No user exists with this username");
            System.out.println(gson.toJson(outputFail));
            return;
        }
        for (BuyList byls : buyLists) {
            if (byls.getUsername().equalsIgnoreCase(buyList.getUsername()) && byls.getCommodityId() == buyList.getCommodityId()) {
                outputFail.setSuccess(false);
                outputFail.setData("This user has already bought this commodity");
                System.out.println(gson.toJson(outputFail));
                return;
            }
        }
        //add to buy list
        buyLists.add(buyList);
    }

    public void removeFromBuyList(String jSon, Gson gson) {
        BuyList buyList = gson.fromJson(jSon, BuyList.class);
        //errors
        if (!userExists(buyList.getUsername())) {
            outputFail.setSuccess(false);
            outputFail.setData("No user exists with this username");
            System.out.println(gson.toJson(outputFail));
            return;
        }
        if (!isCommodityAvailableInBuyList(buyList)) {
            outputFail.setSuccess(false);
            outputFail.setData("This commodity doesn't exist in your buyList");
            System.out.println(gson.toJson(outputFail));
            return;
        }
        //remove from buy list
        for (int i = 0; i < buyLists.size(); i++) {
            if (buyList.getUsername().equalsIgnoreCase(buyLists.get(i).getUsername()) && buyList.getCommodityId() == buyLists.get(i).getCommodityId()) {
                buyLists.remove(i);
                return;
            }
        }
    }

    public void getCommodityById(String jSon, Gson gson) {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        GetCommodityById getCommodityById = gson.fromJson(jSon, GetCommodityById.class);
        //errors
        if (commodityDoesntExists(getCommodityById.getId())) {
            outputFail.setSuccess(false);
            outputFail.setData("No commodity exists with this commodityId");
            System.out.println(gson.toJson(outputFail));
            return;
        }
        //get commodity by id
        int i = 0;
        for (Commodity cmd : commodities) {
            if (cmd.getId() == getCommodityById.getId()) {
                jsonObject.add("id", JsonParser.parseString(gson.toJson(commodities.get(i).getId())));
                jsonObject.add("name", JsonParser.parseString(gson.toJson(commodities.get(i).getName())));
                int j = 0;
                for (Provider prv : providers) {
                    if (prv.getId() == cmd.getProviderId()) {
                        jsonObject.add("provider", JsonParser.parseString(gson.toJson(providers.get(j).getName())));
                    }
                    j++;
                }
                jsonObject.add("price", JsonParser.parseString(gson.toJson(commodities.get(i).getPrice())));
                for (String cat : commodities.get(i).getCategories()) {
                    jsonArray.add(cat);
                }
                jsonObject.add("categories", jsonArray);
                jsonObject.add("rating", JsonParser.parseString(gson.toJson(commodities.get(i).getRating())));
            }
            i++;
        }
        outputSuccess.setSuccess(true);
        outputSuccess.setData(jsonObject);
        System.out.println(gson.toJson(outputSuccess));
    }

    public void getCommodityByCategory(String jSon, Gson gson) {
        JsonArray jsonArray2 = new JsonArray();
        GetCommodityByCategory getCommodityByCategory = gson.fromJson(jSon, GetCommodityByCategory.class);
        for (Commodity cmd : commodities) {
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            if (cmd.getCategories().contains(getCommodityByCategory.getCategory())){
                jsonObject.addProperty("id",cmd.getId());
                jsonObject.addProperty("name", cmd.getName());
                for (Provider prv : providers) {
                    if (prv.getId() == cmd.getProviderId()) {
                        jsonObject.addProperty("provider", prv.getName());
                    }
                }
                jsonObject.addProperty("price", cmd.getPrice());
                for (String cat : cmd.getCategories()) {
                    jsonArray.add(cat);
                }
                jsonObject.add("categories", jsonArray);
                jsonObject.addProperty("rating", cmd.getRating());
                jsonArray2.add(jsonObject);
            }
        }
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.add("commoditiesListByCategory", jsonArray2);
        outputSuccess.setSuccess(true);
        outputSuccess.setData(jsonObject2);
        System.out.println(gson.toJson(outputSuccess));
    }

    public void getBuyList(String jSon, Gson gson){
        JsonArray jsonArray = new JsonArray();
        GetBuyList getBuyList = gson.fromJson(jSon, GetBuyList.class);
        for (BuyList byls : buyLists){
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray2 = new JsonArray();
            if(byls.getUsername().equalsIgnoreCase(getBuyList.getUsername())){
                jsonObject.addProperty("id",byls.getCommodityId());
                jsonObject.addProperty("name",byls.getUsername());
                for(Commodity cmd : commodities){
                    if (byls.getCommodityId() == cmd.getId()){
                        jsonObject.addProperty("providerId",cmd.getProviderId());
                        jsonObject.addProperty("price", cmd.getPrice());
                        for(String cat : cmd.getCategories()){
                            jsonArray2.add(cat);
                        }
                        jsonObject.add("categories", jsonArray2);
                        jsonObject.addProperty("rating", cmd.getRating());
                        jsonArray.add(jsonObject);
                    }
                }
            }
        }
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.add("buylist", jsonArray);
        outputSuccess.setSuccess(true);
        outputSuccess.setData(jsonObject2);
        System.out.println(gson.toJson(outputSuccess));
    }

    public boolean usernameValidation(String username){
        for (char c : username.toCharArray()){
            if(!Character.isLetterOrDigit(c))
                return false;
        }
        return true;
    }

    public boolean providerValidation(Provider provider){
        for (char c : provider.getName().toCharArray()){
            if(!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        for (Provider prv : providers){
            if(prv.getId() == provider.getId()) {
                return false;
            }
        }
        return true;
    }

    public boolean commodityDoesntExists(int getCommodityId){
        for(Commodity cmd : commodities){
            if(cmd.getId() == getCommodityId) {
                return false;
            }
        }
        return true;
    }

    public boolean isCommodityAvailable(int getCommodityId){
        int i=0;
        for(Commodity cmd : commodities){
            if(cmd.getId() == getCommodityId){
                if(commodities.get(i).getInStock()>0)
                    return true;
            }
            i++;
        }
        return false;
    }

    public boolean userExists(String username){
        for(User usr : users){
            if(usr.getUsername().equalsIgnoreCase(username))
                return true;
        }
        return false;
    }

    public boolean isCommodityAvailableInBuyList(BuyList buyList){
        for(BuyList byls : buyLists){
            if(byls.getCommodityId() == buyList.getCommodityId() && byls.getUsername().equalsIgnoreCase(buyList.getUsername()))
                return true;
        }
        return false;
    }
}
