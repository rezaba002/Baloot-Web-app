package org.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.classes.Store;

import java.io.IOException;

@WebServlet ("/applyDiscount")
public class ApplyDiscount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Store store = Store.getInstance();
        String username = store.getCurrentUser().getUsername();

        String discountCode = req.getParameter("discountCode");

        if (!store.checkIfDiscountExists(discountCode)) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.setStatus(400);
            req.getSession().setAttribute("errorMessage", "Discount Code Is Invalid!");
            resp.sendRedirect("/error");
        }

        else if (store.checkIfDiscountIsExpired(username, discountCode)) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.setStatus(400);
            req.getSession().setAttribute("errorMessage", "Discount Code Has Been Expired!");
            resp.sendRedirect("/error");
        }

        else {
            resp.setContentType("text/html;charset=UTF-8");
            resp.setStatus(200);
            req.getSession().setAttribute("discountCode", discountCode);
            resp.sendRedirect("/buyList");
        }
    }
}
