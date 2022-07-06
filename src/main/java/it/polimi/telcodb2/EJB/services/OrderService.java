package it.polimi.telcodb2.EJB.services;

import it.polimi.telcodb2.EJB.entities.*;
import it.polimi.telcodb2.EJB.entities.Package;
import it.polimi.telcodb2.EJB.utils.Pair;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
        LocalDate activationDate = order.getStartDate().isAfter(LocalDate.now()) ? order.getStartDate() : LocalDate.now();
        Schedule schedule = new Schedule(
                activationDate,
                activationDate.plusMonths(order.getValidity().getDuration()),
                order.getCustomer(),
                order.getProducts(),
                order.getPackage().getServices()
        );

        // If customer is insolvent
        Customer customer = order.getCustomer();
        Alert alert = null;
        if (!customer.isSolvent()) {
            // If there are no more pending orders
            List<Order> pendingOrders = em.createNamedQuery("Order.findPendingByIdCustomer", Order.class)
                    .setParameter("idCustomer", customer.getIdCustomer())
                    .getResultList();
            if (pendingOrders.isEmpty()) {
                // Set customer as solvent
                customer.setSolvent(true);
                customer.setFailedPayments(0);
                em.merge(customer);
                // Check if there is an alert for the customer [then remove it - POSTPONED]
                Optional<Alert> optAlert = em.createNamedQuery("Alert.findByCustomerId", Alert.class)
                        .setParameter("idCustomer", customer.getIdCustomer())
                        .getResultStream().findFirst();
                if (optAlert.isPresent()) {
                    alert = optAlert.get();
                }
            }
        }

        // Commit changes
        try {
            em.persist(schedule);
            em.merge(order);

            em.merge(customer);
            if (alert != null) {
                em.remove(alert);
            }

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

        // Create order object as summary
        // TODO: create order summary class
        return new Order(startDate, validity, products, pkg);
    }

    public Order findById(int orderId) {
        if (orderId < 0) { // sanity check
            return null;
        }

        Order order = em.find(Order.class, orderId);
        order.getPackage();
        order.getValidity();
        order.getProducts();
        return order;
    }
}
