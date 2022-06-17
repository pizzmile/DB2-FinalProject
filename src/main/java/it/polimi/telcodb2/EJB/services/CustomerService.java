package it.polimi.telcodb2.EJB.services;

import it.polimi.telcodb2.EJB.entities.Customer;
import it.polimi.telcodb2.EJB.entities.Employee;
import it.polimi.telcodb2.EJB.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class CustomerService {

    @PersistenceContext
    private EntityManager em;

    /**
     * Check that there is a customer in the database that matches some given credentials.
     * @param username username to look for
     * @param password password to verify
     * @return the customer entity object the credentials match, else null
     * @throws CredentialsException if any error occurs during the fetching of the data from the database
     * @throws NonUniqueResultException if there are more than one customer with the same username
     */
    public Customer checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
        List<Customer> customerList;

        // Get customers given the username
        try {
            customerList = em.createNamedQuery("Customer.findByUsername", Customer.class)
                    .setParameter("username", username)
                    .getResultList();

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

    /*
    public Customer checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
        List<Customer> customerList = null;

        try {
            customerList = em.createNamedQuery("Customer.checkCredentials", Customer.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentials");
        }

        if (customerList.isEmpty())
            return null;
        else if (customerList.size() == 1)
            return customerList.get(0);

        throw new NonUniqueResultException("More than one user registered with same credentials");

    }
     */
}
