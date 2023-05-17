package org.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.classes.Store;

import java.io.IOException;

@WebServlet ("/removeFromBuyList")
public class RemoveFromBuyList extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Store store = Store.getInstance();

        String username = store.getCurrentUser().getUsername();

        int commodityId = Integer.valueOf(req.getParameter("commodityId"));

        store.removeFromBuyList(username, commodityId);

        resp.setContentType("text/html;charset=UTF-8");
        resp.setStatus(200);
        resp.sendRedirect("/buyList");
    }
}
