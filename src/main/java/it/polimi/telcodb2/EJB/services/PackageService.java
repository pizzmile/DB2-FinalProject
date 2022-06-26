package it.polimi.telcodb2.EJB.services;

import it.polimi.telcodb2.EJB.entities.Package;
import it.polimi.telcodb2.EJB.entities.Product;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

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

    // TODO: implementing package insertion
    /**
     * Create new package and insert it into the database
     * @param name name of the package
     * @param fee fee of the product
     * @return the product if everything went good, else null
     */
    public Package createPackage(String name, int duration, float fee, List<Product> optionalProducts) {
        // Create new customer entity object
        Package aPackage = new Package(name, duration, fee);

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
