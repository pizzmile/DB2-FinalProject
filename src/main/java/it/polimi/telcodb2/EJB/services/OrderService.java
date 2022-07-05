package it.polimi.telcodb2.EJB.services;

import it.polimi.telcodb2.EJB.entities.*;
import it.polimi.telcodb2.EJB.entities.Package;
import it.polimi.telcodb2.EJB.utils.Pair;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Stateless
public class OrderService {

    @PersistenceContext
    private EntityManager em;

    public Order createOrder(LocalDate startDate, Integer customerId, List<Integer> productIds, Integer packageId, Integer validityId) {
        // Check if there are null parameters
        if (startDate == null || customerId == null || productIds == null || packageId == null || validityId == null) {
            return null;
        }

        // Find customer
        Customer customer = em.find(Customer.class, customerId);
        // Find products
        // TODO: test if no products broke it
        List<Product> products = productIds.stream().map(id -> em.find(Product.class, id)).collect(Collectors.toList());
        // Find package
        Package pkg = em.find(Package.class, packageId);
        // Find validity
        Validity validity = em.find(Validity.class, validityId);

        // Create order
        Order order = new Order(startDate, customer, products, pkg, validity);
        try {
            em.persist(order);
            em.flush();
            return order;
        } catch (ConstraintViolationException e) {
            return null;
        }
    }

    public Schedule setPaymentSuccess(Integer idOrder) {
        // Check if order id is valid
        if (idOrder == null) {
            return null;
        }

        // Find and update order payment status
        Order order = em.find(Order.class, idOrder);
        order.setPaid(true);

        // Create activation schedule
        Schedule schedule = new Schedule(
                order.getStartDate(),
                order.getStartDate().plusMonths(order.getValidity().getDuration()),
                order.getCustomer(),
                order.getProducts(),
                order.getPackage().getServices()
        );

        // Commit changes
        try {
            em.persist(schedule);
            em.merge(order);
            em.flush();

            return schedule;
        } catch (ConstraintViolationException e) {
            return null;
        }
    }

    public Order getSummary(Integer validityId, List<Integer> productIdList, Integer packageId, LocalDate startDate) {
        // TODO: fetch.LAZY may be better than EAGER
        // Check if there are null parameters
        if (validityId == null || productIdList == null || productIdList.stream().anyMatch(Objects::isNull) || startDate == null) {
            return null;
        }

        // Find validity
        Validity validity = em.find(Validity.class, validityId);
        // Find products
        List<Product> products = productIdList.stream().map(id -> em.find(Product.class, id)).collect(Collectors.toList());
        // Find package
        Package pkg = em.find(Package.class, packageId);

        // Create order object
        return new Order(startDate, validity, products, pkg);
    }
}
