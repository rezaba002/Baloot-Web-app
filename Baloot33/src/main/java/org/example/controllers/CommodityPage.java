package org.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import org.example.classes.Store;

import java.io.IOException;
import java.util.StringTokenizer;

@WebServlet("/commodities/*")
public class CommodityPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Store store = Store.getInstance();
        if (!store.checkIfCurrentUserIsLoggedIn()) {
            resp.sendRedirect("/login");
            return;
        }


        StringTokenizer tokenizer = new StringTokenizer(req.getRequestURI(), "/");
        String command = tokenizer.nextToken();
        String id = tokenizer.nextToken();

        resp.setContentType("text/html;charset=UTF-8");
        resp.setStatus(200);
        req.setAttribute("id", id);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/commodity.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
