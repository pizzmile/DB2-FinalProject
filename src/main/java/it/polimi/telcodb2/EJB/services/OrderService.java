package it.polimi.telcodb2.EJB.services;

import it.polimi.telcodb2.EJB.entities.*;
import it.polimi.telcodb2.EJB.entities.Package;
import it.polimi.telcodb2.EJB.utils.OrderSummary;
import it.polimi.telcodb2.EJB.utils.PaymentService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class OrderService {

    @PersistenceContext
    private EntityManager em;

    /**
     * Create a default order
     * @param startDate start date chosen with the order
     * @param customerId id of the customer that made the order
     * @param productIds ids of the products chosen with the order
     * @param packageId id of the package included in the order
     * @param validityId id of the validity chosen with the order
     * @return the order object if everything went ok, else null
     */
    public Order createOrder(LocalDate startDate, Integer customerId, List<Integer> productIds, Integer packageId, Integer validityId) {
        // Check if there are null parameters
        if (startDate == null || customerId == null || productIds == null || packageId == null || validityId == null) {
            return null;
        }

        // Find customer
        Customer customer = em.find(Customer.class, customerId);
        // Find products
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

    /**
     * Update the payment status of an order as paid and implement the logic to update the related entities (
     * 1. create an activation schedule
     * 2. update customer solvent status
     * 3. update the alert triggered by the customer).
     * @param idOrder the id of the order update
     * @return the activation scheduled related to the object
     */
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

    /**
     * Create an order summary object fetching the minimum required data from the database
     * @param validityId id of the validity chosen with the order
     * @param productIdList list of ids of the products chosen with the order
     * @param packageId id of the package included in the order
     * @param startDate start date chosen with the order
     * @return the order summary object if passed data are valid, else null
     */
    public OrderSummary getSummary(Integer validityId, List<Integer> productIdList, Integer packageId, LocalDate startDate) {
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
        return new OrderSummary(startDate, validity, products, pkg);
    }

    /**
     * Find an order rin the database given its id and build an order summary object
     * @param orderId id of the order in the database
     * @return the order submary object
     */
    public OrderSummary findSummary(int orderId) {
        if (orderId < 0) { // sanity check
            return null;
        }

        Order order = em.find(Order.class, orderId);
        return new OrderSummary(
                order.getIdOrder(),
                order.getStartDate(),
                order.getCreationDateTime(),
                order.getValidity(),
                order.getProducts(),
                order.getPackage(),
                order.getTotalCost()
        );
    }

    /**
     * Fin an order in the database given its id
     * @param orderId id of the order in the database
     * @return the order entity object if there is one matching the given id, else null
     */
    public Order findById(int orderId) {
        if (orderId < 0) { // sanity check
            return null;
        }

        return em.find(Order.class, orderId);
    }

    public int payOrder(OrderSummary orderSummary, int idCustomer) {
        // Simulate payment service
        boolean paid = PaymentService.pay();
//        paid = false;
        paid = true;

        // Check if summary is valid
        if (orderSummary == null || orderSummary.getIdOrder() < 0 && orderSummary.getIdOrder() != -1) {
            return 2;
        }

        // TRY
        try {
            // Find the owner of the order
            Customer customer = em.find(Customer.class, idCustomer);

            // If paid
            if (paid) {
                Order order;
                // If order is new
                if (orderSummary.getIdOrder() == -1) {
                    // Find related entities
                    Validity validity = em.find(Validity.class, orderSummary.getValidity().getIdValidity());
                    List<Product> productList = orderSummary.getProducts()
                            .stream()
                            .map(product -> em.find(Product.class, product.getIdProduct()))
                            .collect(Collectors.toList());
                    Package pkg = em.find(Package.class, orderSummary.getPackage().getIdPackage());

                    // Check if entities are valid
                    if (validity == null || productList.stream().anyMatch(Objects::isNull) || pkg == null || customer == null) {
                        return 3;
                    }

                    // Create order
                    order = new Order(orderSummary.getStartDate(), customer, productList, pkg, validity, true);
                    em.persist(order);
                }
                // If order has already been created
                else {
                    // Find order
                    order = em.find(Order.class, orderSummary.getIdOrder());
                    // Check if order is valid
                    if (order == null) {
                        return 4;
                    }

                    // Update payment status
                    order.setPaid(true);
                    em.merge(order);

                    // If there are no more pending order the customer become solvent
                    List<Order> orderList = em.createNamedQuery("Order.findPendingByIdCustomer", Order.class).setParameter("idCustomer", idCustomer).getResultList();
                    if (orderList.isEmpty()) {
                        customer.setSolvent(true);
                        customer.setFailedPayments(0);
                        em.merge(customer);

                        // If there was an alert remove it
                        Optional<Alert> optionalAlert = em.createNamedQuery("Alert.findByCustomerId", Alert.class).setParameter("idCustomer", idCustomer).getResultStream().findFirst();
                        if (optionalAlert.isPresent()) {
                            Alert alert = optionalAlert.get();
                            em.remove(alert);
                        }
                    }
                }

                // Compute activation date and create schedule
                LocalDate activationDate = order.getStartDate().isAfter(LocalDate.now()) ? order.getStartDate() : LocalDate.now();
                Schedule schedule = new Schedule(
                        activationDate,
                        activationDate.plusMonths(order.getValidity().getDuration()),
                        order.getCustomer(),
                        order.getProducts(),
                        order.getPackage().getServices()
                );
                em.persist(schedule);

                em.flush();

                return 0;
            }
            else {
                Order order;

                // If order is new
                if (orderSummary.getIdOrder() == -1) {
                    // Find related entities
                    Validity validity = em.find(Validity.class, orderSummary.getValidity().getIdValidity());
                    List<Product> productList = orderSummary.getProducts()
                            .stream()
                            .map(product -> em.find(Product.class, product.getIdProduct()))
                            .collect(Collectors.toList());
                    Package pkg = em.find(Package.class, orderSummary.getPackage().getIdPackage());

                    // Check if entities are valid
                    if (
                            validity == null ||
                            productList.stream().anyMatch(Objects::isNull) ||
                            pkg == null ||
                            customer == null
                    ) {
                        return 5;
                    }

                    // Create order
                    order = new Order(orderSummary.getStartDate(), customer, productList, pkg, validity, false);
                    em.persist(order);
                } else{
                    order = em.find(Order.class, orderSummary.getIdOrder());
                }

                // Set customer as insolvent and increase failed payments
                if (customer.isSolvent()) {
                    customer.setSolvent(false);
                }
                customer.increaseFailedPayments();

                // If it was the third failed payment, then create an alert
                if (customer.getFailedPayments() == 3) {
                    Alert alert = new Alert(
                            LocalDateTime.now(),
                            order.getTotalCost(),
                            customer.getEmail(),
                            customer.getUsername(),
                            customer
                    );
                    em.persist(alert);
                }
                // If there have been already 3 failed payments, then update the alert
                else if (customer.getFailedPayments() > 3) {
                    Alert alert = em.createNamedQuery("Alert.findByCustomerId", Alert.class).setParameter("idCustomer", idCustomer).getSingleResult();
                    alert.setAmount(order.getTotalCost());
                    alert.setLastPayment(LocalDateTime.now());
                    em.merge(alert);
                }

                em.merge(customer);
                em.flush();

                return 1;
            }
        }
        // CATCH
        catch (ConstraintViolationException e) {
            return -1;
        }

    }
}
