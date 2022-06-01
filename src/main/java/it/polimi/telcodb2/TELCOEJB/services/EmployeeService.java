package it.polimi.telcodb2.TELCOEJB.services;

import it.polimi.telcodb2.TELCOEJB.entities.Employee;
import it.polimi.telcodb2.TELCOEJB.exceptions.CredentialsException;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;


public class EmployeeService {
    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    public EmployeeService(){

    }

    // Method to verify if the employee is correct
    public Employee checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
        List<Employee> employeeList = null;
        try {
            employeeList = em.createNamedQuery("Employee.checkCredentials", Employee.class).setParameter(1, username).setParameter(2, password).getResultList();
        } catch (PersistenceException e){
            throw new CredentialsException(" Could not verify the credentials.");
        }

        if (employeeList.size() == 1){
            return employeeList.get(0);
        }
        else if (employeeList.isEmpty()){
            return null;
        }
        else {
            throw new NonUniqueResultException("More than one user with the same credentials.");
        }
    }
}
