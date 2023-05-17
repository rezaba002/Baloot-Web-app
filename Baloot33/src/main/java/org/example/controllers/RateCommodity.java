package org.example.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.classes.Store;

import java.io.IOException;

@WebServlet ("/rate")
public class RateCommodity extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Store store = Store.getInstance();

        String username = store.getCurrentUser().getUsername();

        int commodityId = Integer.valueOf(req.getParameter("commodityId"));
        int score = Integer.valueOf(req.getParameter("score"));

        store.rateCommodity(username, commodityId, score);

        resp.setContentType("text/html;charset=UTF-8");
        resp.setStatus(200);
        resp.sendRedirect("/commodities/" + commodityId);
//        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/commodity.jsp");
//        requestDispatcher.forward(req, resp);
    }
}
