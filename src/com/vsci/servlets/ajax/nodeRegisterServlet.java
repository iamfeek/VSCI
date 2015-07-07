package com.vsci.servlets.ajax;

import com.vsci.methods.GenerateUUID;

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
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        System.out.println("Registered Client's IP: " + request.getRemoteAddr());
        String uuid = request.getParameter("uuid");
        System.out.println("Registered Client's UUID: " + uuid);

        //check if client uuid is in the DB.
        if (request.getParameter("uuid").equals("f490419fc32ada6eb741b7ae2dfd0935bd28ecae")) {
            response.getWriter().write("wb");
        } else {
            response.getWriter().write(GenerateUUID.gen());
        }
    }
}
