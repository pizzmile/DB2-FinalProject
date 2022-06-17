package it.polimi.telcodb2.WEB.controllers;

import it.polimi.telcodb2.EJB.entities.Customer;
import it.polimi.telcodb2.EJB.exceptions.CredentialsException;
import it.polimi.telcodb2.EJB.services.CustomerService;
import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginCustomer", value = "/login-customer")
public class LoginCustomer extends HttpServlet {

    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.telcodb2.EJB.services/CustomerService")
    private CustomerService customerService;

    public LoginCustomer() {
        super();
    }


    public void init() {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }


    // GET requests are not allowed
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "GET is not allowed");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Read parameters from request
        String username = null;
        String password = null;
        try {
            username = StringEscapeUtils.escapeJava(request.getParameter("username"));
            password = StringEscapeUtils.escapeJava(request.getParameter("password"));
            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                throw new Exception("Missing or empty credentials");
            }

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty credentials");
            return;
        }

        Customer customer;
        try {
            customer = customerService.checkCredentials(username, password);
        } catch (CredentialsException | NonUniqueResultException e) {
            //e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong username or password");
            return;
        }

        // If the user exists, add info to the session and go to home page
        // else show login page with error message
        String path;
        if (customer != null) {
            request.getSession().setAttribute("user", customer);
            path = getServletContext().getContextPath() + "/customer-home.html";
            response.sendRedirect(path);
        } else {
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            ctx.setVariable("errorMsg", "Wrong username or password");
            ctx.setVariable("usernameVal", username);
            ctx.setVariable("passwordVal", password);
            path = "/customer-login.html";
            templateEngine.process(path, ctx, response.getWriter());
        }
    }


    public void destroy() {}
}