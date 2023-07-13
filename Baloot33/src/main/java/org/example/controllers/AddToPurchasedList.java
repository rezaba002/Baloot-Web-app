package org.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.classes.BuyList;
import org.example.classes.Store;

import java.io.IOException;

@WebServlet ("/addToPurchasedList")
public class AddToPurchasedList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Store store = Store.getInstance();

        String username = store.getCurrentUser().getUsername();

        int userCredit = Integer.valueOf(req.getParameter("credit"));
        int finalPrice = Integer.valueOf(req.getParameter("finalPrice"));
        String discountCode = req.getParameter("discountCode");

        int newCredit = userCredit - finalPrice;

        //check if credit is enough
        if (userCredit < finalPrice) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.setStatus(400);
            req.getSession().setAttribute("errorMessage", "Not Enough Credit");
            resp.sendRedirect("/error");
        }
        else {
            //add buyList to purchasedList
            for (BuyList buyListObj : store.getBuyLists()) {
                if (buyListObj.getUsername().equalsIgnoreCase(username))
                    store.addToPurchasedList(username, buyListObj.getCommodityId());
            }
            //empty buyList
            for (int i = store.getBuyLists().size()-1 ; i >= 0 ; i--) {
                if (store.getBuyLists().get(i).getUsername().equalsIgnoreCase(username))
                    store.removeFromBuyList(username, store.getBuyLists().get(i).getCommodityId());
            }
            //update user's credit
            for (int i = 0 ; i < store.getUsers().size() ; i++) {
                if (store.getUsers().get(i).getUsername().equalsIgnoreCase(username)) {
                    store.getUsers().get(i).setCredit(newCredit);
                    break;
                }
            }
            //expire discount code if used
            if (!discountCode.equalsIgnoreCase("#"))
                store.addExpiredDiscount(username, discountCode);

            resp.setContentType("text/html;charset=UTF-8");
            resp.setStatus(200);
            req.getSession().setAttribute("discountCode", "#");
            req.getSession().setAttribute("successMessage",  "Thanks For Your Purchase, " + username + " !");
            resp.sendRedirect("/success");
       }
    }
}
