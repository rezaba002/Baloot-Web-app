package org.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.classes.Commodity;
import org.example.classes.Store;

import java.io.IOException;

@WebServlet ("/addToBuyList")
public class AddToBuyList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Store store = Store.getInstance();

        String username = store.getCurrentUser().getUsername();
        int commodityId = Integer.valueOf(req.getParameter("commodityId"));

        String commodityName = "";
        for (Commodity commodityObj : store.getCommodities()) {
            if (commodityObj.getId() == commodityId)
                commodityName = commodityObj.getName();
        }

        if (store.checkIfCommodityExistsInBuyList(username, commodityId)) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.setStatus(400);
            req.getSession().setAttribute("errorMessage", "Commodity Already Exists In BuyList!");
            resp.sendRedirect("/error");
        }

        else if (store.checkIfCommodityIsOutOfStock(commodityId)) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.setStatus(400);
            req.getSession().setAttribute("errorMessage", commodityName + " Is Out Of Stock!");
            resp.sendRedirect("/error");
        }

        else {
            store.addToBuyList(username, commodityId);

            resp.setContentType("text/html;charset=UTF-8");
            resp.setStatus(200);
            req.getSession().setAttribute("successMessage", commodityName + " Is Added to " + username + "'s BuyList!");
            resp.sendRedirect("/success");
        }
    }
}
