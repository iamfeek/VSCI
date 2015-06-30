package com.vsci.servlets.ajax;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by: Syafiq Hanafee
 * Dated: 29/6/15.
 */
public class nodeRegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("got it!");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
    }
}
