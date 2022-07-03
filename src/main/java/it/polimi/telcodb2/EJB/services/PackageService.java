package it.polimi.telcodb2.EJB.services;

import it.polimi.telcodb2.EJB.entities.Package;
import it.polimi.telcodb2.EJB.entities.Product;
import it.polimi.telcodb2.EJB.entities.Service;
import it.polimi.telcodb2.EJB.entities.Validity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class PackageService {

    @PersistenceContext
    private EntityManager em;

    /**
     * Fetch packages by name
     * @param name name of the product to look for
     * @return the list containing the products matching the name
     */
    public List<Package> findByName(String name) {
        return em.createNamedQuery("Product.findByName", Package.class)
                .getResultList();
    }

    /**
     * Create new package and insert it into the database
     * @param name name of the package
     * @param validityList list of compatible validities
     * @param serviceList lis of included service
     * @param productIdList list of optional products
     * @return the product if everything went good, else null
     */
    public Package createPackage(String name, List<Validity> validityList, List<Integer> serviceIdList, List<Integer> productIdList) {
        // Get optional product entity objects
        List<Product> productList = productIdList.stream()
                .map(id -> em.find(Product.class, id))
                .collect(Collectors.toList());
        // Get included service entity objects
        List<Service> serviceList = serviceIdList.stream()
                .map(id -> em.find(Service.class, id))
                .collect(Collectors.toList());

        // Create new customer entity object
        Package aPackage = new Package(name, validityList, serviceList, productList);
        // Try to store the new customer into the database
        try {
            em.persist(aPackage);
            em.flush();
            return aPackage;
        } catch (ConstraintViolationException e) {
            return null;
        }
    }
}
