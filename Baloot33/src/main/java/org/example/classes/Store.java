package org.example.classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Store {

//----------------------------------------------------------------------------------------------------------------------
//                                                    Variables
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

    private ArrayList<Commodity> tempCommodities = new ArrayList<Commodity>();
    public ArrayList<Commodity> getTempCommodities() {return tempCommodities;}
    public void setTempCommodities(ArrayList<Commodity> tempCommodities) {this.tempCommodities = tempCommodities;}

    private ArrayList<Comment> comments = new ArrayList<Comment>();
    public ArrayList<Comment> getComments() {return comments;}
    public void setComments(ArrayList<Comment> comments) {this.comments = comments;}

    private ArrayList<Discount> discounts = new ArrayList<Discount>();
    public ArrayList<Discount> getDiscounts() {return discounts;}
    public void setDiscounts(ArrayList<Discount> discounts) {this.discounts = discounts;}

    public ArrayList<ExpiredDiscount> expiredDiscounts = new ArrayList<ExpiredDiscount>();
    public ArrayList<ExpiredDiscount> getExpiredDiscounts() {return expiredDiscounts;}
    public void setExpiredDiscounts(ArrayList<ExpiredDiscount> expiredDiscounts) {this.expiredDiscounts = expiredDiscounts;}


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

    private CurrentUser currentUser = new CurrentUser();
    public CurrentUser getCurrentUser() {return currentUser;}
    public void setCurrentUser(CurrentUser currentUser) {this.currentUser = currentUser;}

    private static Store store = null;
    private Store(){
        commodities = new ArrayList<>();
        comments = new ArrayList<>();
        users = new ArrayList<>();
        providers = new ArrayList<>();
        rateCommodities = new ArrayList<>();
        voteComments = new ArrayList<>();
        buyLists = new ArrayList<>();
        purchasedLists = new ArrayList<>();
    }
    public static Store getInstance() {
        if(store == null) {
            store = new Store();
            try {
                ApiHandler.downloadData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return store;
    }

//----------------------------------------------------------------------------------------------------------------------
//                                                     Methods
//----------------------------------------------------------------------------------------------------------------------

    public void addUser(HttpClient client,HttpRequest request,Gson gson) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type usersList = new TypeToken<ArrayList<User>>(){}.getType();
        users = gson.fromJson(response.body(), usersList);
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

    public void addDiscount (HttpClient client,HttpRequest request,Gson gson) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type discountsList = new TypeToken<ArrayList<Discount>>(){}.getType();
        discounts = gson.fromJson(response.body(), discountsList);
    }

    public void addExpiredDiscount (String username, String discountCode) {
        ExpiredDiscount expiredDiscountObj = new ExpiredDiscount();
        expiredDiscountObj.setUsername(username);
        expiredDiscountObj.setDiscountCode(discountCode);
        expiredDiscounts.add(expiredDiscountObj);
    }

    public void addCredit(String username, int addingCredit) {
        for (User userObj : users) {
            if (userObj.getUsername().equalsIgnoreCase(username))
                userObj.setCredit(userObj.getCredit() + addingCredit);
        }
    }

    public void commentCommodity (String username, int commodityId, String text) {
        Comment commentObj = new Comment();
        commentObj.setCommentId(comments.size()+1);
        for (User userObj : users) {
            if (userObj.getUsername().equalsIgnoreCase(username)) {
                commentObj.setUserEmail(userObj.getEmail());
            }
        }
        commentObj.setCommodityId(commodityId);
        commentObj.setText(text);
        commentObj.setDate(String.valueOf(java.time.LocalDate.now()));
        commentObj.setLike(0);
        commentObj.setDislike(0);

        comments.add(commentObj);
    }
    public void rateCommodity(String username, int commodityId, int score) {
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

    public void voteComment(String username, int commentId, int vote) {
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
                            if (voteCommentObject.getVote() == voteComments.get(j).getVote()) {
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

    public void addToBuyList(String username, int commodityId) {
        BuyList buyListObject = new BuyList();
        buyListObject.setUsername(username);
        buyListObject.setCommodityId(commodityId);
        buyLists.add(buyListObject);
    }

    public void removeFromBuyList(String username, int commodityId) {
        for (int i = 0; i < buyLists.size(); i++) {
            if (buyLists.get(i).getUsername().equalsIgnoreCase(username) && buyLists.get(i).getCommodityId() == commodityId) {
                buyLists.remove(i);
                return;
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

    public void setCurrentUser(String username) {
        for (User userObj : users) {
            if (userObj.getUsername().equalsIgnoreCase(username))
                currentUser.setUsername(userObj.getUsername());
        }
    }

    public void clearCurrentUser() {
        currentUser.setUsername(null);
    }

    public void sortCommoditiesByRating() {
        for (int i = 0 ; i < tempCommodities.size() - 1 ; i++) {
            for (int j = i+1 ; j < tempCommodities.size() ; j++) {
                if (tempCommodities.get(i).getRating() < tempCommodities.get(j).getRating()) {
                    Commodity tempCommodity = tempCommodities.get(j);
                    tempCommodities.set(j, tempCommodities.get(i));
                    tempCommodities.set(i, tempCommodity);
                }
            }
        }
    }

    public void sortCommoditiesByPrice() {
        for (int i = 0 ; i < tempCommodities.size() - 1 ; i++) {
            for (int j = i+1 ; j < tempCommodities.size() ; j++) {
                if (tempCommodities.get(i).getPrice() < tempCommodities.get(j).getPrice()) {
                    Commodity tempCommodity = tempCommodities.get(j);
                    tempCommodities.set(j, tempCommodities.get(i));
                    tempCommodities.set(i, tempCommodity);
                }
            }
        }
    }


//----------------------------------------------------------------------------------------------------------------------
//                                                     Checks
//----------------------------------------------------------------------------------------------------------------------


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
    public boolean checkIfCurrentUserIsLoggedIn() {
        if (currentUser.getUsername() == null)
            return false;
        return true;
    }
    public boolean checkIfCredentialsIsValid(String username, String password) {
        for (User userObj : users) {
            if (userObj.getUsername().equalsIgnoreCase(username) && userObj.getPassword().equalsIgnoreCase(password))
                return true;
        }
        return false;
    }
    public boolean checkIfDiscountExists(String discountCode) {
        for (Discount discountObj : discounts) {
            if (discountObj.getDiscountCode().equalsIgnoreCase(discountCode))
                return true;
        }
        return false;
    }
    public boolean checkIfDiscountIsExpired(String username, String discountCode) {
        for (ExpiredDiscount expiredDiscountObj : expiredDiscounts) {
            if (expiredDiscountObj.getUsername().equalsIgnoreCase(username) && expiredDiscountObj.getDiscountCode().equalsIgnoreCase(discountCode))
                return true;
        }
        return false;
    }
}
