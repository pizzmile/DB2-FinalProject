package it.polimi.telcodb2.WEB.controllers;

import it.polimi.telcodb2.EJB.entities.Product;
import it.polimi.telcodb2.EJB.services.ProductService;
import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateProduct", value = "/create-product")
public class CreateProduct extends HttpServlet {

    @EJB
    private ProductService productService;


    public CreateProduct() {
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
        String name = StringEscapeUtils.escapeJava(request.getParameter("name"));
        float fee = Float.parseFloat(request.getParameter("fee"));

        if (name == null || name.isEmpty() || fee <= 0) {
            // TODO: change (not for this project)
            response.sendRedirect(getServletContext().getContextPath() + "/registration?missing=true");
            return;
        }

        if (!productService.findByName(name).isEmpty()) {
            // TODO: change (not for this project)
            response.sendRedirect(getServletContext().getContextPath() + "/registration?duplicated=true");
            return;
        }

        Product product = productService.createProduct(name, fee);
        System.out.println(product);
        if (product == null) {
            // TODO: add to context that the operation was not successful
            response.sendRedirect(getServletContext().getContextPath() + "/employee-home.html");
            return;
        }
        // TODO: add to context that the operation was successful
        response.sendRedirect(getServletContext().getContextPath() + "/employee-home.html");

    }
}
