package it.polimi.telcodb2.EJB.services;

import it.polimi.telcodb2.EJB.entities.Alert;
import it.polimi.telcodb2.EJB.entities.Customer;
import it.polimi.telcodb2.EJB.entities.Employee;
import it.polimi.telcodb2.EJB.entities.Order;
import it.polimi.telcodb2.EJB.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class CustomerService {

    @PersistenceContext
    private EntityManager em;

    // Fetch customers by username
    public List<Customer> findByUsername(String username) {
        return em.createNamedQuery("Customer.findByUsername", Customer.class)
                .setParameter("username", username)
                .getResultList();
    }

    // Fetch customers by email
    public List<Customer> findByEmail(String email) {
        return em.createNamedQuery("Customer.findByEmail", Customer.class)
                .setParameter("email", email)
                .getResultList();
    }

    /**
     * Create new customer and insert it into the database.
     * @param username the username of the customer
     * @param password the password of the customer
     * @param email the email of the password
     * @return the customer entity object if the operation was successful, else null
     */
    public Customer createCustomer(String username, String password, String email) {
        // Create new customer entity object
        Customer customer = new Customer(
                username, password, email);

        // Try to store the new customer into the database
        try {
            em.persist(customer);
            em.flush();
            return customer;
        } catch (ConstraintViolationException e) {
            return null;
        }
    }

    /**
     * Check that there is a customer in the database that matches some given credentials.
     * @param username username to look for
     * @param password password to verify
     * @return the customer entity object the credentials match, else null
     * @throws CredentialsException if any error occurs during the fetching of the data from the database
     * @throws NonUniqueResultException if there are more than one customer with the same username
     */
    public Customer checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
        // Fetch customers by username
        List<Customer> customerList;
        try {
            customerList = findByUsername(username);
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentials");
        }

        // If there are no customers, then return null
        // if there is only one customer, then check the password
        // else (there are more customers for the same username) throw exception
        if (customerList.isEmpty()) {
            return null;

        } else if (customerList.size() == 1) {
            Customer customer = customerList.get(0);
            // If the password match, then return the customer
            // else return null
            if (customer.getPassword().equals(password)) {
                return customer;
            } else {
                return null;
            }

        } else {
            throw new NonUniqueResultException("More than one user registered with the same credentials");
        }
    }

    public Integer increaseFailedPayments(int customerId, float amount) {
        // Check if params are valid
        if (customerId < 0 || amount < 0) {
            return null;
        }

        // Find customer
        Customer customer = em.find(Customer.class, customerId);
        // Increment failed payments by 1
        customer.setFailedPayments(customer.getFailedPayments() + 1);
        // If customer is solvent, set is as insolvent
        if (customer.isSolvent()) {
            customer.setSolvent(false);
        }
        // If failed payments reach 3, then create an alert
        if (customer.getFailedPayments() == 3) {
            // Create alert
            Alert alert = new Alert(
                    LocalDateTime.now(),
                    amount,
                    customer.getEmail(),
                    customer.getUsername(),
                    customer
            );

            // Commit changes
            try {
                em.merge(customer);
                em.persist(alert);
                em.flush();
                return customer.getFailedPayments();
            } catch (ConstraintViolationException e) {
                return null;
            }
        }
        else if (customer.getFailedPayments() > 3) {
            // Find existing alert
            Alert alert = em.createNamedQuery("Alert.findByCustomerId", Alert.class)
                    .setParameter("idCustomer", customer.getIdCustomer())
                    .getSingleResult();
            if (alert == null) {
                return null;
            }

            alert.setLastPayment(LocalDateTime.now());
            alert.setAmount(amount);

            // Commit changes
            try {
                em.merge(customer);
                em.merge(alert);
                em.flush();
                return customer.getFailedPayments();
            } catch (ConstraintViolationException e) {
                return null;
            }
        }

        return customer.getFailedPayments();
    }

    public List<Order> findPendingOrders(int customerId) {
        // Check if customer is valid
        if (customerId < 0) {
            return null;
        }

        // Find pending orders
        List<Order> pendingOrders = em.createNamedQuery("Order.findPendingByIdCustomer", Order.class)
                .setParameter("idCustomer", customerId)
                .getResultList();
        pendingOrders.forEach(Order::getPackage);
        return pendingOrders;
    }
}
