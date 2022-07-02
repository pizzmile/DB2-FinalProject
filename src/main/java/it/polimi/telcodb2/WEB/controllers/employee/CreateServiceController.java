package it.polimi.telcodb2.WEB.controllers.employee;

import it.polimi.telcodb2.EJB.entities.Service;
import it.polimi.telcodb2.EJB.services.ServiceService;
import it.polimi.telcodb2.EJB.utils.data_handlers.ServiceDataHandler;
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
import java.util.List;

@WebServlet(name = "CreateService", value = "/create-service")
public class CreateServiceController extends HttpServlet {

    @EJB
    private ServiceService serviceService;

    public CreateServiceController() {super();}

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
        // Check if session is valid
        HttpSession session = request.getSession();
        if (session.getAttribute("username") == null || session.getAttribute("userid") == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid sessions");
        }

        ServiceDataHandler serviceData = ServiceDataHandler.parseServiceData(
                request.getParameter("type"),
                request.getParameter("minutes"),
                request.getParameter("extra-minutes"),
                request.getParameter("sms"),
                request.getParameter("extra-sms"),
                request.getParameter("giga"),
                request.getParameter("extra-giga")
        );
        if (serviceData == null) {
            response.sendRedirect(getServletContext().getContextPath() + "/employee-home?error=Empty service field");
            return;
        }

        List<Service> serviceList = serviceService.findEquivalents(
                serviceData.getType(),
                serviceData.getMinutes(),
                serviceData.getExtraMinutesFee(),
                serviceData.getSms(),
                serviceData.getExtraSmsFee(),
                serviceData.getGiga(),
                serviceData.getExtraGigaFee()
        );
        if (serviceList.isEmpty()) {
            Service service = serviceService.createService(
                    serviceData.getType(),
                    serviceData.getMinutes(),
                    serviceData.getExtraMinutesFee(),
                    serviceData.getSms(),
                    serviceData.getExtraSmsFee(),
                    serviceData.getGiga(),
                    serviceData.getExtraGigaFee()
            );
            if (service == null) {
                response.sendRedirect(getServletContext().getContextPath() + "/employee-home?error=An unexpected error occurred! Try again.");
            }
            else {
                response.sendRedirect(getServletContext().getContextPath() + "/employee-home?success=True");
            }
        } else {
            response.sendRedirect(getServletContext().getContextPath() + "/employee-home?error=Already existing service");
        }

    }
}
