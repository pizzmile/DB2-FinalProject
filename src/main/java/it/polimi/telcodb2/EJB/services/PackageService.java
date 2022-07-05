package it.polimi.telcodb2.EJB.services;

import it.polimi.telcodb2.EJB.entities.Package;
import it.polimi.telcodb2.EJB.entities.Product;
import it.polimi.telcodb2.EJB.entities.Service;
import it.polimi.telcodb2.EJB.entities.Validity;
import it.polimi.telcodb2.EJB.utils.Pair;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
     * @param validityIdList list of ids of compatible validities
     * @param serviceIdList lis of ids of included service
     * @param productIdList list of ids of optional products
     * @return the product if everything went good, else null
     */
    public Package createOnlyPackage(String name, List<Integer> validityIdList, List<Integer> serviceIdList, List<Integer> productIdList) {
        // Get optional product entity objects
        List<Product> productList = productIdList.stream()
                .map(id -> em.find(Product.class, id))
                .collect(Collectors.toList());
        // Get included service entity objects
        List<Service> serviceList = serviceIdList.stream()
                .map(id -> em.find(Service.class, id))
                .collect(Collectors.toList());
        // Get included validity entity objects
        List<Validity> validityList = validityIdList.stream()
                .map(id -> em.find(Validity.class, id))
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

    /**
     * Create a package and every related entity that does not exist
     * @param name
     * @param validityData
     * @param serviceIds
     * @param productIds
     * @return
     */
    public Package createPackage(String name, List<Pair<Integer, Float>> validityDataList, List<Integer> serviceIdList, List<Integer> productIdList) {
        // Get optional product entity objects
        List<Product> productList = productIdList.stream()
                .map(id -> em.find(Product.class, id))
                .collect(Collectors.toList());
        // Get included service entity objects
        List<Service> serviceList = serviceIdList.stream()
                .map(id -> em.find(Service.class, id))
                .collect(Collectors.toList());
        // Get or create compatible validity entity objects
        List<Validity> validityList = validityDataList.stream()
                .map(data -> {
                    Validity tmpValidity;
                    List<Validity> tmpValidityList = em.createNamedQuery("Validity.findEquivalent", Validity.class)
                            .setParameter("duration", data.getX())
                            .setParameter("fee", data.getY())
                            .getResultList();
                    if (tmpValidityList.isEmpty()) {    // If there are no equivalent validities, create a new one
                        tmpValidity = new Validity(data.getX(), data.getY());
                        em.persist(tmpValidity);
                        em.flush();
                    } else { // else, take the first (only one)
                        tmpValidity = tmpValidityList.get(0);
                    }
                    return tmpValidity;
                })
                .collect(Collectors.toList());
//        Validity newValidity = new Validity(duration, fee);
//        List<Validity> validityList = validityIdList.stream()
//                .map(id -> em.find(Validity.class, id))
//                .collect(Collectors.toList());

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

    /**
     * Fetch all packages in database
     * @return the list of all packages
     */
    public List<Package> findAll() {
        return em.createNamedQuery("Package.findAll", Package.class)
                .getResultList();
    }

    /**
     * Fetch package by id
     * @param id package id
     * @return the package entity object or null
     */
    public Package find(int id) {
        return em.find(Package.class, id);
    }
}
