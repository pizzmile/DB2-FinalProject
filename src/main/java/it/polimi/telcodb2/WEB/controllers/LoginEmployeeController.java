package it.polimi.telcodb2.WEB.controllers;

import it.polimi.telcodb2.EJB.entities.Employee;
import it.polimi.telcodb2.EJB.exceptions.CredentialsException;
import it.polimi.telcodb2.EJB.services.EmployeeService;
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

@WebServlet(name = "LoginEmployee", value = "/login-employee")
public class LoginEmployeeController extends HttpServlet {

    private TemplateEngine templateEngine;

    private static final long serialVersionUID = 1L;

    @EJB(name = "it.polimi.telcodb2.EJB.services/EmployeeService")
    private EmployeeService employeeService;

    public LoginEmployeeController() {
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


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // obtain and escape params
        String username = StringEscapeUtils.escapeJava(request.getParameter("username"));
        String password = StringEscapeUtils.escapeJava(request.getParameter("password"));
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty credentials");
            return;
        }

        Employee employee;
        try {
            employee = employeeService.checkCredentials(username, password);
        } catch (CredentialsException | NonUniqueResultException e) {
//            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
            return;
        }

        // If the user exists, add info to the session and go to home page, otherwise
        // show login page with error message
        String path;
        if (employee == null) {
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
//            ctx.setVariable("errorMsg", "Wrong username or password.");
//            ctx.setVariable("usernameVal", username);
//            ctx.setVariable("passwordVal", password);
//            path = "/employee-login.html";
//            templateEngine.process(path, ctx, response.getWriter());
            path = getServletContext().getContextPath() + "/employee-login?error=Wrong username or password";
            response.sendRedirect(path);
        } else{
            request.getSession().setAttribute("user", employee);
            path = getServletContext().getContextPath() + "/employee-home";
//            path = getServletContext().getContextPath() + "/customer-employee-home.html";
            response.sendRedirect(path);
        }
    }

    public void destroy() {
    }
}

