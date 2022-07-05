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

    public Order getSummary(Integer validityId, List<Integer> productIdList, Integer packageId, LocalDate startDate) {
        // TODO: fetch.LAZY may be better than EAGER
        // Check fi there are null parameters
        if (validityId == null || productIdList == null || productIdList.stream().anyMatch(Objects::isNull) || startDate == null) {
            return null;
        }

        // Find validity
        Validity validity = em.find(Validity.class, validityId);
        // Find products
        List<Product> products = productIdList.stream().map(id -> em.find(Product.class, id)).collect(Collectors.toList());
        // Find package
        Package pkg = em.find(Package.class, packageId);

        // Sum the fees
        float totalCost = products.stream().map(Product::getFee).reduce(validity.getFee(), Float::sum) * validity.getDuration();

        // Create order object
        return new Order(startDate, null, totalCost, false, validity, products, pkg);
    }
}
