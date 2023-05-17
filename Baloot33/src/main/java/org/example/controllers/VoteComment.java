package org.example.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.classes.Store;

import java.io.IOException;

@WebServlet ("/voteComment")
public class VoteComment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Store store = Store.getInstance();

        String username = store.getCurrentUser().getUsername();

        int commentId = Integer.valueOf(req.getParameter("commentId"));
        String commodityId = req.getParameter("commodityId");

        if (req.getParameter("action").equalsIgnoreCase("like")) {
            store.voteComment(username, commentId, 1);
        }

        else if (req.getParameter("action").equalsIgnoreCase("dislike")) {
            store.voteComment(username, commentId, -1);
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.setStatus(200);
        resp.sendRedirect("/commodities/"+ commodityId);
//        req.setAttribute("id", commodityId);
//        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/commodity.jsp");
//        requestDispatcher.forward(req, resp);
    }
}
