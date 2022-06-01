package it.polimi.telcodb2.TELCOWEB.controllers;


import it.polimi.telcodb2.TELCOEJB.entities.Employee;
import it.polimi.telcodb2.TELCOEJB.exceptions.CredentialsException;
import it.polimi.telcodb2.TELCOEJB.services.EmployeeService;
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
import org.apache.commons.lang.StringEscapeUtils;


@WebServlet("/LoginEmployee")
public class LoginEmployee extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.telcodb2.TELCOEJB.services/EmployeeService")
    private EmployeeService employeeService;

    public LoginEmployee() {
        super();
    }

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // obtain and escape params
        String username = null;
        String password = null;
        try {
            username = StringEscapeUtils.escapeJava(request.getParameter("username"));
            password = StringEscapeUtils.escapeJava(request.getParameter("password"));
            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                throw new Exception("Missing or empty credential value");
            }
        } catch (Exception e) {
            // for debugging only e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
            return;
        }
        Employee employee;
        try {
            // query db to authenticate for user
            employee = employeeService.checkCredentials(username, password);
        } catch (CredentialsException | NonUniqueResultException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
            return;
        }

        // If the employee exists, add info to the session and go to employee page, otherwise
        // show login page with error message

        String path;
        if (employee == null) {
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            ctx.setVariable("errorMsg", "Incorrect username or password");
            ctx.setVariable("usernameVal", username);
            ctx.setVariable("passwordVal", password);
            path = "/loginEmployee.html";
            templateEngine.process(path, ctx, response.getWriter());
        } else {
            request.getSession().setAttribute("user", employee);
            path = getServletContext().getContextPath() + "/employee";
            response.sendRedirect(path);
        }

    }

    public void destroy() {
    }
}