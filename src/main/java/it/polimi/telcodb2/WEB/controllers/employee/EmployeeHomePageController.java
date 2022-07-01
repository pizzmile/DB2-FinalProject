package it.polimi.telcodb2.WEB.controllers.employee;

import it.polimi.telcodb2.EJB.entities.Product;
import it.polimi.telcodb2.EJB.services.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "EmployeeHomePageController", value = "/employee-home")
public class EmployeeHomePageController extends HttpServlet {
    private final String templatePath;
    private final String pathPrefix;
    private final String pathSuffix;
    protected TemplateMode templateMode;
    protected TemplateEngine templateEngine;

    @EJB
    private ProductService productService;

    public EmployeeHomePageController() {
        this.templateEngine = new TemplateEngine();
        this.templatePath = "employee-home";
        this.templateMode = TemplateMode.HTML;
        this.pathPrefix = "";
        this.pathSuffix = ".html";
    }

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);

        templateResolver.setTemplateMode(templateMode);
        templateResolver.setSuffix(pathSuffix);
        templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setPrefix(pathPrefix);
    }

    protected void processTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processTemplate(request, response, null);
    }

    protected void processTemplate(HttpServletRequest request, HttpServletResponse response,
                                   Map<String, Object> variables) throws IOException {
        ServletContext servletCtx = getServletContext();

        final WebContext webCtx = new WebContext(request, response, servletCtx, request.getLocale());

        if (variables != null) {
            webCtx.setVariables(variables);
        }

        templateEngine.process(templatePath, webCtx, response.getWriter());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") == null || session.getAttribute("userid") == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid sessions");
        }

        HashMap<String, Object> context = new HashMap<>();

        List<Product> products = productService.findAll();
        context.put("products", products);

        this.processTemplate(request, response, context);
    }

}
