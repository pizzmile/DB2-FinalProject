package it.polimi.telcodb2.EJB.services;

import it.polimi.telcodb2.EJB.entities.Validity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class ValidityService {

    @PersistenceContext
    private EntityManager em;

    /**
     * Fetch validities by duration and fee
     * @param duration the duration of the validity
     * @param fee the fee of the validity
     * @return the list of matching validity entity objects
     */
    public List<Validity> findEquivalents(int duration, float fee) {
        return em.createNamedQuery("Validity.findByDurationFee", Validity.class)
                .setParameter("duration", duration)
                .setParameter("fee", fee)
                .getResultList();
    }

    /**
     * Create new validity entity object and add it to the database
     * @param duration duration of the validity
     * @param fee fee of the validity
     * @return the validity entity object if everything went right, else null
     */
    public Validity createValidity(int duration, float fee) {
        Validity newValidity = new Validity(duration, fee);

        try {
            em.persist(newValidity);
            em.flush();
            return newValidity;
        } catch (ConstraintViolationException e) {
            return null;
        }
    }
}
