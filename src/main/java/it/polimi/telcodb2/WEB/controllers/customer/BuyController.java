package it.polimi.telcodb2.WEB.controllers.customer;

import it.polimi.telcodb2.EJB.entities.Customer;
import it.polimi.telcodb2.EJB.entities.Order;
import it.polimi.telcodb2.EJB.entities.Product;
import it.polimi.telcodb2.EJB.entities.Schedule;
import it.polimi.telcodb2.EJB.services.CustomerService;
import it.polimi.telcodb2.EJB.services.OrderService;
import it.polimi.telcodb2.EJB.utils.PaymentService;
import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "BuyController", value = "/buy")
public class BuyController extends HttpServlet {

    @EJB
    private OrderService orderService;


    public BuyController() {
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
        String path;

        // Parse parameters from session
        HttpSession session = request.getSession();
        Object orderObject = session.getAttribute("order");
        // Check if order is null, else cast it as an order object
        if (orderObject == null) {
            // TODO: redirect with error
            return;
        }
        Order tmpOrder = (Order) orderObject;

        // Parse values fo order
        LocalDate startDate = tmpOrder.getStartDate();
        List<Integer> productIds = tmpOrder.getProducts().stream().map(Product::getIdProduct).collect(Collectors.toList());
        Integer packageId = tmpOrder.getPackage().getIdPackage();
        Integer validityId = tmpOrder.getValidity().getIdValidity();
        // If customer id is in session, then parse it, otherwise redirect with error
        Integer customerId;
        if (session.getAttribute("userid") == null) {
            // TODO: redirect with error
            return;
        } else {
            customerId = (Integer) session.getAttribute("userid");
        }

        // Create order object and add it to the database
        Order newOrder = orderService.createOrder(startDate, customerId, productIds, packageId, validityId);
        if (newOrder == null) {
            // TODO: redirect with error
            return;
        }

        // Simulate the payment
        boolean paymentSuccess = PaymentService.pay();
        // If payment succeed, then update its payment status and create an activations schedule
        if (paymentSuccess) {
            Schedule schedule = orderService.setPaymentSuccess(newOrder.getIdOrder());
            path = getServletContext().getContextPath() + "/customer-home?success=Order completed";
            response.sendRedirect(path);
            return;
        }
        // If payment failed
        else {
            return;
        }
    }
}
