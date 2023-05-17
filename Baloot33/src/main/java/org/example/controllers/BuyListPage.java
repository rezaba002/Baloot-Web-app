package org.example.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.classes.BuyList;
import org.example.classes.Commodity;
import org.example.classes.Store;

import java.io.IOException;

@WebServlet ("/buyList")
public class BuyListPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Store store = Store.getInstance();
        if (!store.checkIfCurrentUserIsLoggedIn()) {
            resp.sendRedirect("/login");
            return;
        }

        String username = store.getCurrentUser().getUsername();

        int priceSum = 0;
        for (BuyList buyListObj : store.getBuyLists()) {
            if (buyListObj.getUsername().equalsIgnoreCase(username)) {
                for (Commodity commodityObj : store.getCommodities()) {
                    if (buyListObj.getCommodityId() == commodityObj.getId()) {
                        priceSum += commodityObj.getPrice();
                    }
                }
            }
        }

        String price = Integer.toString(priceSum);
        req.getSession().setAttribute("fixedPrice", price);

        if (req.getSession().getAttribute("discountCode") == null)
            req.getSession().setAttribute("discountCode", "#");
        else
            req.getSession().setAttribute("discountCode", req.getSession().getAttribute("discountCode"));

        resp.setContentType("text/html;charset=UTF-8");
        resp.setStatus(200);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/buyList.jsp");
        requestDispatcher.forward(req, resp);
    }
}
