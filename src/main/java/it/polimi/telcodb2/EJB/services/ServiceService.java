package it.polimi.telcodb2.EJB.services;

import it.polimi.telcodb2.EJB.entities.Service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ServiceService {

    @PersistenceContext
    private EntityManager em;

    public Service createService(int type) {
        Service service = new Service(type);
        return service;
    }

    public Service createService(int type, int minutes, float extraMinutesFee, int sms, float extraSmsFee) {
        Service service = new Service(type, minutes, extraMinutesFee, sms, extraSmsFee);
        return service;
    }

    public Service createService(int type, int giga, float extraGigaFee) {
        Service service = new Service(type, giga, extraGigaFee);
        return service;
    }
}
