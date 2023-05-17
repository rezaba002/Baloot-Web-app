package org.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.classes.Store;

import java.io.IOException;

@WebServlet ("/comment")
public class AddComment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Store store = Store.getInstance();

        String username = store.getCurrentUser().getUsername();
        int commodityId = Integer.valueOf(req.getParameter("commodityId"));
        String commentText = req.getParameter("commentText");

        if (commentText.equalsIgnoreCase("")) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.setStatus(400);
            req.getSession().setAttribute("errorMessage", "Can't Submit An Empty Comment!");
            resp.sendRedirect("/error");
        }
        else {
            store.commentCommodity(username, commodityId, commentText);

            resp.setContentType("text/html;charset=UTF-8");
            resp.setStatus(200);
            req.getSession().setAttribute("successMessage", username + "'s Comment Has Been Submitted!");
            resp.sendRedirect("/success");
        }
    }
}
