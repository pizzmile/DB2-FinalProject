package it.polimi.telcodb2.EJB.services;

import it.polimi.telcodb2.EJB.entities.Employee;
import it.polimi.telcodb2.EJB.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class EmployeeService {
    @PersistenceContext
    private EntityManager em;

    public Employee checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
        List<Employee> employeeList = null;
        try {
            employeeList = em.createNamedQuery("Employee.checkCredentials", Employee.class).setParameter(1, username).setParameter(2, password)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentials");
        }
        if (employeeList.isEmpty())
            return null;
        else if (employeeList.size() == 1)
            return employeeList.get(0);
        throw new NonUniqueResultException("More than one user registered with same credentials");

    }
}
