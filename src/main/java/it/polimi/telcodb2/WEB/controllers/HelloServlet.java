package it.polimi.telcodb2.WEB.controllers;

import java.io.*;
import java.sql.DriverManager;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;
    private static final long serialVersionUID = 1L;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String DB_URL = "jdbc:mysql://localhost:3306/TelcoDB"; //Replace with your own configuration
        final String USER = "admin"; //Replace with your own configuration
        final String PASS = "admin"; //Replace with your own configuration
        String result = "Connection worked";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            result = "Connection failed"; e.printStackTrace();
        }
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println(result);
        out.close();
    }

    public void destroy() {
    }
}

