package it.polimi.telcodb2.WEB.controllers.employee;

import it.polimi.telcodb2.EJB.entities.Product;
import it.polimi.telcodb2.EJB.services.PackageService;
import it.polimi.telcodb2.EJB.services.ProductService;
import it.polimi.telcodb2.EJB.services.ServiceService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CreatePackage", value="/create-package")
public class CreatePackageController extends HttpServlet {

    @EJB
    private EntityManager em;

    private PackageService packageService;
    private ProductService productService;
    private ServiceService serviceService;


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
        // Parse name
        String name = StringEscapeUtils.escapeJava(request.getParameter("name"));
        // Parse durations
        // TODO: may be empty
        String duration1Raw = request.getParameter("duration-1");
        String fee1Raw = request.getParameter("fee-1");
        String duration2Raw = request.getParameter("duration-2");
        String fee2Raw = request.getParameter("fee-2");
        String duration3Raw = request.getParameter("duration-3");
        String fee3Raw = request.getParameter("fee-3");
        /* Parse services
        //  1) Look for all keys that contains "type" and for each one of them get the service index splitting the already
        //     found key (e.g. "type-1" -> ["type", "1"])
        //  2) Parse the rest of the parameters corresponding to the considered service, according the type
        // Every service is represented by an HashMap containing <key, parameter> and every service is contained in
        // the services ArrayList.
        */
        Map<String, String[]> params = request.getParameterMap();
        ArrayList<HashMap<String, String>> services = new ArrayList<HashMap<String, String>>();
        for (String key: params.keySet()) {
            if (key.contains("type")) {
                HashMap<String, String> service = new HashMap<String, String>();
                String index = key.split("-")[1];
                String serviceType = params.get(key)[0];
                service.put("type", serviceType);
                String giga;
                String extraGiga;
                String minutes;
                String extraMinutes;
                String sms;
                String extraSms;
                switch (serviceType) {
                    case "0":   // FIXED PHONE => type
                        break;
                    case "1":   // FIXED INTERNET => type, giga, extra-giga
                    case "3":   // MOBILE INTERNET => type, giga, extra-giga
                        giga = StringEscapeUtils.escapeJava(request.getParameter("giga-" + index));
                        extraGiga = StringEscapeUtils.escapeJava(request.getParameter("extra-giga-" + index));
                        service.put("giga", giga);
                        service.put("extra_giga", extraGiga);
                        break;
                    case "2":   // MOBILE PHONE => type, minutes, extra-minutes, sms, extra-sms
                        minutes = StringEscapeUtils.escapeJava(request.getParameter("minutes-" + index));
                        extraMinutes = StringEscapeUtils.escapeJava(request.getParameter("extra-minutes-" + index));
                        sms = StringEscapeUtils.escapeJava(request.getParameter("sms-" + index));
                        extraSms = StringEscapeUtils.escapeJava(request.getParameter("extra-sms-" + index));
                        service.put("minutes", minutes);
                        service.put("extra_minutes", extraMinutes);
                        service.put("sms", sms);
                        service.put("extra_sms", extraSms);
                        break;
                    default:
                        break;
                }
                services.add(service);
            }
        }
        // Parse products
        ArrayList<Integer> productIds = new ArrayList<Integer>();
        for (String rawProductId : request.getParameterValues("opt-products")) {
            productIds.add(Integer.parseInt(rawProductId));
        }

        // Check if session is valid
        HttpSession session = request.getSession();
        if (session.getAttribute("username") == null || session.getAttribute("userid") == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid sessions");
        }

    }
}
