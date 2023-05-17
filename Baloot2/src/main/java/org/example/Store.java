package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.example.exceptions.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Store {


//----------------------------------------------------------------------------------------------------------------------
//                                                     Arrays
//----------------------------------------------------------------------------------------------------------------------


    private ArrayList<User> users = new ArrayList<User>();
    public ArrayList<User> getUsers() {return users;}
    public void setUsers(ArrayList<User> users) {this.users = users;}

    private ArrayList<Provider> providers = new ArrayList<Provider>();
    public ArrayList<Provider> getProviders() {return providers;}
    public void setProviders(ArrayList<Provider> providers) {this.providers = providers;}

    private ArrayList<Commodity> commodities = new ArrayList<Commodity>();
    public ArrayList<Commodity> getCommodities() {return commodities;}
    public void setCommodities(ArrayList<Commodity> commodities) {this.commodities = commodities;}

    private ArrayList<Comment> comments = new ArrayList<Comment>();
    public ArrayList<Comment> getComments() {return comments;}
    public void setComments(ArrayList<Comment> comments) {this.comments = comments;}

    private ArrayList<RateCommodity> rateCommodities = new ArrayList<RateCommodity>();
    public ArrayList<RateCommodity> getRateCommodities() {return rateCommodities;}
    public void setRateCommodities(ArrayList<RateCommodity> rateCommodities) {this.rateCommodities = rateCommodities;}

    private ArrayList<VoteComment> voteComments = new ArrayList<VoteComment>();
    public ArrayList<VoteComment> getVoteComments() {return voteComments;}
    public void setVoteComments(ArrayList<VoteComment> voteComments) {this.voteComments = voteComments;}

    private ArrayList<BuyList> buyLists = new ArrayList<BuyList>();
    public ArrayList<BuyList> getBuyLists() {return buyLists;}
    public void setBuyLists(ArrayList<BuyList> buyLists) {this.buyLists = buyLists;}

    private ArrayList<PurchasedList> purchasedLists = new ArrayList<PurchasedList>();
    public ArrayList<PurchasedList> getPurchasedLists() {return purchasedLists;}
    public void setPurchasedLists(ArrayList<PurchasedList> purchasedLists) {this.purchasedLists = purchasedLists;}



//----------------------------------------------------------------------------------------------------------------------
//                                                     Methods
//----------------------------------------------------------------------------------------------------------------------


    public void addUser(HttpClient client,HttpRequest request,Gson gson) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type usersList = new TypeToken<ArrayList<User>>(){}.getType();
        users = gson.fromJson(response.body(), usersList);

        User clientUser = new User();
        clientUser.setUsername("clientUser");
        clientUser.setPassword("clientUserPassword");
        clientUser.setEmail("clientUserEmail");
        clientUser.setBirthDate("clientUserBirthDate");
        clientUser.setAddress("clientUserAddress");
        clientUser.setCredit(0);
        users.add(clientUser);
    }

    public void addProvider(HttpClient client,HttpRequest request,Gson gson) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type providersList = new TypeToken<ArrayList<Provider>>(){}.getType();
        providers = gson.fromJson((String) response.body(), providersList);
    }

    public void addCommodity(HttpClient client,HttpRequest request,Gson gson) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type commoditiesList = new TypeToken<ArrayList<Commodity>>(){}.getType();
        commodities = gson.fromJson((String) response.body(), commoditiesList);

        for (Commodity commodityObj : commodities)
            commodityObj.setNumberOfVoters(1);
    }

    public void addComment (HttpClient client,HttpRequest request,Gson gson) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type commentsList = new TypeToken<ArrayList<Comment>>(){}.getType();
        comments = gson.fromJson(response.body(), commentsList);

        for (int i=0 ; i < comments.size() ; i++)
            comments.get(i).setCommentId(i+1);
    }

    public void rateCommodity(String username, int commodityId, int score) throws Exception {

        if (!checkIfUserExists(username))
            throw new UserNotFound400Exp();

        if (!checkIfCommodityExists(commodityId))
            throw new CommodityNotFound400Exp();

        if (!checkIfScoreIsValid(score))
            throw new InvalidScore400Exp();

        else {
            RateCommodity rateCommodityObject = new RateCommodity();
            rateCommodityObject.setUsername(username);
            rateCommodityObject.setCommodityId(commodityId);
            rateCommodityObject.setScore(score);

            int i = 0;
            for (Commodity commodityObj : commodities) {
                //update score of rating
                for (int j = 0; j < rateCommodities.size(); j++) {
                    if (rateCommodityObject.getUsername().equalsIgnoreCase(rateCommodities.get(j).getUsername()) && rateCommodityObject.getCommodityId() == commodityObj.getId()) {
                        commodities.get(i).setRating((float) ((commodities.get(i).getRating() * commodities.get(i).getNumberOfVoters()) - rateCommodities.get(j).getScore()) / (commodities.get(i).getNumberOfVoters() - 1));
                        rateCommodities.set(j, rateCommodityObject);
                        commodities.get(i).setRating((float) (rateCommodityObject.getScore() + (commodities.get(i).getRating() * (commodities.get(i).getNumberOfVoters() - 1))) / commodities.get(i).getNumberOfVoters());
                        return;
                    }
                }
                //add score to rating
                if (rateCommodityObject.getCommodityId() == commodityObj.getId()) {
                    rateCommodities.add(rateCommodityObject);
                    commodities.get(i).setRating((float) (rateCommodityObject.getScore() + (commodities.get(i).getRating() * commodities.get(i).getNumberOfVoters())) / (commodities.get(i).getNumberOfVoters() + 1));
                    commodities.get(i).setNumberOfVoters(commodities.get(i).getNumberOfVoters() + 1);
                }
                i++;
            }
        }
    }

    public void voteComment(String username, int commentId, int vote) throws Exception {

        if (!checkIfUserExists(username))
            throw new UserNotFound400Exp();

        if (!checkIfCommentExists(commentId))
            throw new CommentNotFound400Exp();

        if (!checkIfVoteIsValid(vote))
            throw new InvalidVote400Exp();

        else {
            VoteComment voteCommentObject = new VoteComment();
            voteCommentObject.setUsername(username);
            voteCommentObject.setCommentId(commentId);
            voteCommentObject.setVote(vote);

            int i = 0;
            for (Comment commentObj : comments) {
                for (User userObj : users) {
                    if (userObj.getEmail().equalsIgnoreCase(commentObj.getUserEmail())) {
                        for (int j=0 ; j < voteComments.size() ; j++) {
                            if (voteCommentObject.getUsername().equalsIgnoreCase(voteComments.get(j).getUsername()) && voteCommentObject.getCommentId() == voteComments.get(j).getCommentId()) {
                                if (voteCommentObject.getUsername().equalsIgnoreCase("clientUser") && voteCommentObject.getVote() == voteComments.get(j).getVote()) {
                                    voteComments.remove(j);
                                    updateVote(voteCommentObject.getCommentId());
                                    return;
                                }
                                voteComments.set(j, voteCommentObject);
                                updateVote(voteCommentObject.getCommentId());
                                return;
                            }
                        }
                        if (voteCommentObject.getCommentId() == commentObj.getCommentId()) {
                            voteComments.add(voteCommentObject);
                            updateVote(voteCommentObject.getCommentId());
                            return;
                        }
                        i++;
                    }
                }
            }
        }
    }

    public void updateVote(int commentId) {
        int likes = 0;
        int dislikes = 0;
        for (Comment commentObj : comments) {
            if (commentObj.getCommentId() == commentId) {
                for (VoteComment voteCommentObj : voteComments) {
                    if (voteCommentObj.getCommentId() == commentId) {
                        if (voteCommentObj.getVote() == 1)
                            likes++;
                        if (voteCommentObj.getVote() == -1)
                            dislikes++;
                    }
                }
                commentObj.setLike(likes);
                commentObj.setDislike(dislikes);
            }
        }
    }

    public void addToBuyList(String username, int commodityId) throws Exception {

        if (!checkIfUserExists(username))
            throw new UserNotFound400Exp();

        if (!checkIfCommodityExists(commodityId))
            throw new CommodityNotFound400Exp();

        if (checkIfCommodityExistsInBuyList(username, commodityId))
            throw new AlreadyInBuyList400Exp();

        if (checkIfCommodityIsOutOfStock(commodityId))
            throw new OutOfStock400Exp();

        BuyList buyListObject = new BuyList();
        buyListObject.setUsername(username);
        buyListObject.setCommodityId(commodityId);
        buyLists.add(buyListObject);
    }

    public void removeFromBuyList(String username, int commodityId) throws Exception {

        if (!checkIfUserExists(username))
            throw new UserNotFound400Exp();

        if (!checkIfCommodityExists(commodityId))
            throw new CommodityNotFound400Exp();

        if (!checkIfCommodityExistsInBuyList(username, commodityId))
            throw new NotFoundInBuyList400Exp();

        else {
            for (int i = 0; i < buyLists.size(); i++) {
                if (buyLists.get(i).getUsername().equalsIgnoreCase(username) && buyLists.get(i).getCommodityId() == commodityId) {
                    buyLists.remove(i);
                    return;
                }
            }
        }
    }

    public void addToPurchasedList(String username, int commodityId) {
        PurchasedList purchasedListObject = new PurchasedList();
        purchasedListObject.setUsername(username);
        purchasedListObject.setCommodityId(commodityId);
        for(User userObj : users) {
            if(userObj.getUsername().equalsIgnoreCase(username)) {
                for(Commodity commodityObj : commodities) {
                    if(commodityObj.getId() == commodityId) {
                        purchasedLists.add(purchasedListObject);
                        commodityObj.setInStock(commodityObj.getInStock() - 1);
                    }
                }
            }
        }
    }



//----------------------------------------------------------------------------------------------------------------------
//                                                     Checks
//----------------------------------------------------------------------------------------------------------------------


    public boolean checkIfUserExists(String username) {
        for (User userObj : users) {
            if(userObj.getUsername().equalsIgnoreCase(username))
                return true;
        }
        return false;
    }
    public boolean checkIfProviderExists(int providerId) {
        for (Provider providerObj : providers) {
            if (providerObj.getId() == providerId)
                return true;
        }
        return false;
    }
    public boolean checkIfCommodityExists(int commodityId) {
        for(Commodity commodityObj : commodities) {
            if(commodityObj.getId() == commodityId)
                return true;
        }
        return false;
    }
    public boolean checkIfCommentExists(int commentId) {
        for (Comment commentObj : comments) {
            if (commentObj.getCommentId() == commentId)
                return true;
        }
        return false;
    }
    public boolean checkIfCommodityIsOutOfStock(int commodityId) {
        int i=0;
        for(Commodity commodityObj : commodities) {
            if(commodityObj.getId() == commodityId) {
                if(commodities.get(i).getInStock()>0)
                    return false;
            }
            i++;
        }
        return true;
    }
    public boolean checkIfCommodityExistsInBuyList(String username, int commodityId) {
        for(BuyList buyListObj : buyLists) {
            if(buyListObj.getCommodityId() == commodityId && buyListObj.getUsername().equalsIgnoreCase(username))
                return true;
        }
        return false;
    }
    public boolean checkIfCategoryExists(String category) {
        for (Commodity commodityObj : commodities)
            if (commodityObj.getCategories().contains(category))
                return true;
        return false;
    }
    public boolean checkIfCreditIsPositiveNumber(int credit) {
        if (credit >= 0)
            return true;
        return false;
    }
    public boolean checkIfScoreIsValid(int score) {
        if (score <= 10 && score >= 1)
            return true;
        return false;
    }
    public boolean checkIfVoteIsValid(int vote) {
        if (vote == 1 || vote == 0 || vote == -1)
            return true;
        return false;
    }
}