package com.vsci.servlets;

import java.io.IOException;
/**
 * Created by: Syafiq Hanafee
 * Dated: 29/4/15.
 */
public class mainServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.sendRedirect("dashboard");
        System.out.println(request.getHeader("origin"));
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
