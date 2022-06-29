package it.polimi.telcodb2.WEB.controllers;

import it.polimi.telcodb2.EJB.entities.Customer;
import it.polimi.telcodb2.EJB.services.CustomerService;
import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SignupCustomer", value = "/signup-customer")
public class SignupCustomerController extends HttpServlet {

    @EJB
    private CustomerService customerService;


    public SignupCustomerController() {
        super();
    }


    public void init() {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setSuffix(".html");
    }


    // GET requests are not allowed
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "GET is not allowed");
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = StringEscapeUtils.escapeJava(request.getParameter("username"));
        String password = StringEscapeUtils.escapeJava(request.getParameter("password"));
        String email = StringEscapeUtils.escapeJava(request.getParameter("email"));

        if (username == null || username.isEmpty() || password == null || password.isEmpty() || email == null || email.isEmpty()) {
            response.sendRedirect(getServletContext().getContextPath() + "/customer-landing?error=Missing or empty field.");
            return;
        }

        if (!customerService.findByEmail(email).isEmpty()) {
            response.sendRedirect(getServletContext().getContextPath() + "/customer-landing?error=There is already another user for this email.");
            return;
        }
        if (!customerService.findByUsername(username).isEmpty()) {
            response.sendRedirect(getServletContext().getContextPath() + "/customer-landing?error=There is already another user with this username.");
            return;
        }

        Customer customer = customerService.createCustomer(username, password, email);
        if (customer == null) {
            String path = getServletContext().getContextPath() + "/customer-landing?error=Ops! Something went wrong.";
            response.sendRedirect(path);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/customer-landing?success=True");

    }
}
