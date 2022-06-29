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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "IndexController", value = "/index-controller")
public class IndexController extends HttpServlet {
    private TemplateEngine templateEngine;

    public IndexController() {
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
        String action = StringEscapeUtils.escapeJava(request.getParameter("page"));
        String path;
        if (action.equals("customer")){
            request.getSession().setAttribute("user", "tabo");
            path = getServletContext().getContextPath() + "/customer-home.html";
            response.sendRedirect(path);
        } else {
            path = getServletContext().getContextPath() + "/employee-login";
            response.sendRedirect(path);
        }
    }

    public void destroy() {}
}
