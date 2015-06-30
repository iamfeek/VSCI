package com.vsci.servlets;

import com.vsci.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by: Syafiq Hanafee
 * Dated: 29/4/15.
 */
public class loginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Hong Yi does his work here
        System.out.println("GOT IT! Login");

        //get the variables, email & password
        //send this for logic check
        System.out.println(request.getParameter("email"));
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User u = new User(1, "Syafiq Hanafee", 1);
        HttpSession session = request.getSession();
        session.setAttribute("user", u);
        response.sendRedirect("dashboard");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
