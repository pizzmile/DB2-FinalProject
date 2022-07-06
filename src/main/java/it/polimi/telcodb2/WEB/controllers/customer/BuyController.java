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
    @EJB
    private CustomerService customerService;


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
        HttpSession session = request.getSession();

        // If customer id is in session, then parse it, otherwise redirect with error
        Integer customerId;
        if (session.getAttribute("userid") == null) {
            path = getServletContext().getContextPath() + "/customer-home?error=Missing customer id";
            response.sendRedirect(path);
            return;
        } else {
            customerId = (Integer) session.getAttribute("userid");
        }

        // Parse parameters from session
        Object orderObject = session.getAttribute("order");
        // Check if order is null, else cast it as an order object
        if (orderObject == null) {
            path = getServletContext().getContextPath() + "/customer-home?error=Order was empty";
            response.sendRedirect(path);
            return;
        }
        Order orderSummary = (Order) orderObject;

        // If order is new
        Order order;
        if (orderSummary.getIdOrder() == -1) {
            // Parse values for order
            LocalDate startDate = orderSummary.getStartDate();
            List<Integer> productIds = orderSummary.getProducts().stream().map(Product::getIdProduct).collect(Collectors.toList());
            Integer packageId = orderSummary.getPackage().getIdPackage();
            Integer validityId = orderSummary.getValidity().getIdValidity();
            // Create order object and add it to the database
            order = orderService.createOrder(startDate, customerId, productIds, packageId, validityId);
            if (order == null) {
                path = getServletContext().getContextPath() + "/customer-home?error=Ops! Something went wrong while creating the order";
                response.sendRedirect(path);
                return;
            }
        }
        // If order already exists
        else if (orderSummary.getIdOrder() > 0) {
            order = orderService.findById(orderSummary.getIdOrder());
            if (order == null) {
                // TODO: redirect with error
                return;
            }
        }
        else {
            // TODO: redirect with error
            return;
        }

        // Simulate the payment
//        boolean paymentSuccess = PaymentService.pay();
        boolean paymentSuccess = false; // DEBUG
//        boolean paymentSuccess = true;  // DEBUG

        // If payment succeed, then update its payment status and create an activations schedule
        // finally run check this changes the status of the customer to solvent and update alert
        if (paymentSuccess) {
            Schedule schedule = orderService.setPaymentSuccess(order.getIdOrder());
            if (schedule == null) {
                // TODO: redirect with error
                return;
            }
            path = getServletContext().getContextPath() + "/customer-home?success=true";
            response.sendRedirect(path);
        }
        // If payment failed
        else {
            // Payment status is left false
            // Flag user as insolvent
            Integer numOfFailedPayments = customerService.increaseFailedPayments(customerId, order.getTotalCost());
            if (numOfFailedPayments == null) {
                // TODO: redirect with error
                return;
            }
            path = getServletContext().getContextPath() + "/customer-home?warning=Failed payment!";
            response.sendRedirect(path);
        }
    }
}
