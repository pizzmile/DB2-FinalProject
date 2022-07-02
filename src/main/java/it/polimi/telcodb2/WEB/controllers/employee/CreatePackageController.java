package it.polimi.telcodb2.WEB.controllers.employee;

import it.polimi.telcodb2.EJB.entities.Package;
import it.polimi.telcodb2.EJB.entities.Service;
import it.polimi.telcodb2.EJB.entities.Validity;
import it.polimi.telcodb2.EJB.services.PackageService;
import it.polimi.telcodb2.EJB.services.ProductService;
import it.polimi.telcodb2.EJB.services.ServiceService;
import it.polimi.telcodb2.EJB.services.ValidityService;
import it.polimi.telcodb2.EJB.utils.data_handlers.ServiceDataHandler;
import it.polimi.telcodb2.EJB.utils.data_handlers.ValidityDataHandler;
import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "CreatePackage", value="/create-package")
public class CreatePackageController extends HttpServlet {

    @EJB
    private EntityManager em;

    private PackageService packageService;
    private ProductService productService;
    private ServiceService serviceService;
    private ValidityService validityService;


    public CreatePackageController() {
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
        // Check if session is valid
        HttpSession session = request.getSession();
        if (session.getAttribute("username") == null || session.getAttribute("userid") == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid sessions");
        }

        // Parse name
        String name = StringEscapeUtils.escapeJava(request.getParameter("name"));
        // Parse durations
        /*
        Duration and fee may be empty so parse them only if there is a value
         */
        List<ValidityDataHandler> validityDataList = new ArrayList<ValidityDataHandler>();
        String duration1Raw = request.getParameter("duration-1");
        String fee1Raw = request.getParameter("fee-1");
        String duration2Raw = request.getParameter("duration-2");
        String fee2Raw = request.getParameter("fee-2");
        String duration3Raw = request.getParameter("duration-3");
        String fee3Raw = request.getParameter("fee-3");
        if(duration1Raw == null || fee1Raw == null) {
            response.sendRedirect(getServletContext().getContextPath() + "/employee-home?error=Empty field in validity option 1");
            return;
        }
        validityDataList.add(ValidityDataHandler.parseValidityData(duration1Raw, fee1Raw));
        if (duration2Raw != null && fee2Raw != null && !duration2Raw.equals("") && !fee2Raw.equals("")) {
            ValidityDataHandler dataHandler = ValidityDataHandler.parseValidityData(duration2Raw, fee2Raw);
            if (dataHandler == null) {
                response.sendRedirect(getServletContext().getContextPath() + "/employee-home?error=Empty field in validity option 2");
            }
            validityDataList.add(dataHandler);
        } else if ((duration2Raw != null && fee2Raw == null) || (duration2Raw == null && fee2Raw != null)) {
            response.sendRedirect(getServletContext().getContextPath() + "/employee-home?error=Empty field in validity option 2");
            return;
        }
        if (duration3Raw != null && fee3Raw != null && !duration3Raw.equals("") && !fee3Raw.equals("")) {
            ValidityDataHandler dataHandler = ValidityDataHandler.parseValidityData(duration3Raw, fee3Raw);
            if (dataHandler == null) {
                response.sendRedirect(getServletContext().getContextPath() + "/employee-home?error=Empty field in validity option 3");
            }
            validityDataList.add(dataHandler);
        } else if ((duration3Raw != null && fee3Raw == null) || (duration3Raw == null && fee3Raw != null)) {
            response.sendRedirect(getServletContext().getContextPath() + "/employee-home?error=Empty field in validity option 3");
            return;
        }
        // Parse services
        /*  1) Look for all keys that contains "type" and for each one of them get the service index splitting the
        *      already found key (e.g. "type-1" -> ["type", "1"])
        *   2) Parse the rest of the parameters corresponding to the considered service, according the type
        *   Every service is represented by an HashMap containing <key, parameter> and every service is contained in
        *   the services ArrayList.
        */
        Map<String, String[]> params = request.getParameterMap();
        ArrayList<ServiceDataHandler> serviceDataList = new ArrayList<ServiceDataHandler>();
        for (String key: params.keySet()) {
            if (key.contains("type")) {
                HashMap<String, String> service = new HashMap<String, String>();
                String index = key.split("-")[1];
                String serviceType = params.get(key)[0];
                ServiceDataHandler serviceData;
                switch (serviceType) {
                    case "0":   // FIXED PHONE => type
                        serviceDataList.add(
                                ServiceDataHandler.parseServiceData(serviceType)
                        );
                        break;
                    case "1":   // FIXED INTERNET => type, giga, extra-giga
                    case "3":   // MOBILE INTERNET => type, giga, extra-giga
                        serviceDataList.add(
                                ServiceDataHandler.parseServiceData(
                                        serviceType,
                                        request.getParameter("giga-" + index),
                                        request.getParameter("extra-giga-" + index)
                                )
                        );
                        break;
                    case "2":   // MOBILE PHONE => type, minutes, extra-minutes, sms, extra-sms
                        serviceDataList.add(
                                ServiceDataHandler.parseServiceData(
                                        serviceType,
                                        request.getParameter("minutes-" + index),
                                        request.getParameter("extra-minutes-" + index),
                                        request.getParameter("sms-" + index),
                                        request.getParameter("extra-sms-" + index)
                                )
                        );
                        break;
                    default:
                        break;
                }
            }
        }
        if (serviceDataList.isEmpty()) {
            response.sendRedirect(getServletContext().getContextPath() + "/employee-home?error=Empty field in service");
            return;
        }
        // Parse products
        /*
        *   Parse product ids as list of strings, then parse each string id as integer
        */
        ArrayList<Integer> productIdList = new ArrayList<Integer>();
        for (String rawProductId : request.getParameterValues("opt-products")) {
            productIdList.add(Integer.parseInt(rawProductId));
        }

        // Build validity list
        List<Validity> validityList = new ArrayList<Validity>();
        for (ValidityDataHandler dataHandler : validityDataList) {
            validityList.add(safeCreateValidity(dataHandler));
        }
        // Build service list
        List<Service> serviceList =  new ArrayList<Service>();
        for (ServiceDataHandler dataHandler : serviceDataList) {
            serviceList.add(safeCreateService(dataHandler));
        }
        // Build package
        List<Package> existingPackageList = packageService.findByName(name);
        if (existingPackageList.isEmpty()) {
            Package newPackage = packageService.createPackage(name, validityList, serviceList, productIdList);
            if (newPackage == null) {
                response.sendRedirect(getServletContext().getContextPath() + "/employee-home?error=An unexpected error occurred! Try again.");
                return;
            }
            response.sendRedirect(getServletContext().getContextPath() + "/employee-home?success=True");
        } else {
            response.sendRedirect(getServletContext().getContextPath() + "/employee-home?error=Already existing package");
            return;
        }
    }

    private Validity safeCreateValidity(ValidityDataHandler dataHandler) {
        List<Validity> equivalentValidityList = validityService.findEquivalents(
                dataHandler.getDuration(), dataHandler.getFee());
        if (equivalentValidityList.isEmpty()) {
            return validityService.createValidity(dataHandler.getDuration(), dataHandler.getFee());
        }
        return equivalentValidityList.get(0);
    }

    private Service safeCreateService(ServiceDataHandler dataHandler) {
        List<Service> equivalentServiceList = serviceService.findEquivalents(
                dataHandler.getType(),
                dataHandler.getMinutes(), dataHandler.getExtraMinutesFee(),
                dataHandler.getSms(), dataHandler.getExtraSmsFee(),
                dataHandler.getGiga(), dataHandler.getExtraGigaFee()
        );
        if (equivalentServiceList.isEmpty()) {
            return serviceService.createService(
                    dataHandler.getType(),
                    dataHandler.getMinutes(), dataHandler.getExtraMinutesFee(),
                    dataHandler.getSms(), dataHandler.getExtraSmsFee(),
                    dataHandler.getGiga(), dataHandler.getExtraGigaFee()
            );
        }
        return equivalentServiceList.get(0);
    }
}
