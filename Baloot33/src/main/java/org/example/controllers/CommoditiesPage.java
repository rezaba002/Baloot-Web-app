package org.example.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.classes.Store;

import java.io.IOException;

@WebServlet ("/commodities")
public class CommoditiesPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Store store = Store.getInstance();
        if (!store.checkIfCurrentUserIsLoggedIn()) {
            resp.sendRedirect("/login");
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.setStatus(200);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/commodities.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("action").equalsIgnoreCase("search_by_category")) {
            String category = req.getParameter("search");
            req.setAttribute("category", category);
            req.setAttribute("name", "#");
        }

        if (req.getParameter("action").equalsIgnoreCase("search_by_name")) {
            String name = req.getParameter("search");
            req.setAttribute("name", name);
            req.setAttribute("category", "#");
        }

        if (req.getParameter("action").equalsIgnoreCase("sort_by_rate")) {
            req.setAttribute("rate", "true");
            req.setAttribute("price", "false");
        }

        if (req.getParameter("action").equalsIgnoreCase("sort_by_price")) {
            req.setAttribute("price", "true");
            req.setAttribute("rate", "false");
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.setStatus(200);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/commodities.jsp");
        requestDispatcher.forward(req, resp);
    }
}
