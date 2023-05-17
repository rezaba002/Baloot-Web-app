package org.example.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.classes.Store;

import java.io.IOException;

@WebServlet ("/providers")
public class ProvidersPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Store store = Store.getInstance();
        if (!store.checkIfCurrentUserIsLoggedIn()) {
            resp.sendRedirect("/login");
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.setStatus(200);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/providers.jsp");
        requestDispatcher.forward(req, resp);
    }
}
