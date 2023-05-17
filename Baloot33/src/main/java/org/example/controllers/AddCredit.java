package org.example.controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.classes.Store;

import java.io.IOException;

@WebServlet("/credit")
public class AddCredit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Store store = Store.getInstance();
        if (!store.checkIfCurrentUserIsLoggedIn()) {
            resp.sendRedirect("/login");
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.setStatus(200);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/credit.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Store store = Store.getInstance();

        String username = store.getCurrentUser().getUsername();
        int addingCredit =  Integer.valueOf(req.getParameter("credit"));

        store.addCredit(username, addingCredit);

        resp.setContentType("text/html;charset=UTF-8");
        resp.setStatus(200);
        req.getSession().setAttribute("successMessage", addingCredit + " Is Added To " + username + "'s Credit!");
        resp.sendRedirect("/success");
    }
}
