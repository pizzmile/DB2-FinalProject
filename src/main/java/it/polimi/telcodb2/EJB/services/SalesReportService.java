package it.polimi.telcodb2.EJB.services;

import it.polimi.telcodb2.EJB.entities.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Stateless
public class SalesReportService {

    @PersistenceContext
    private EntityManager em;

    public List<AvgProductSoldView> findAvgProductSold() {
        return em.createNamedQuery("AvgProductSoldView.findAll", AvgProductSoldView.class).getResultList();
    }
    public BestSellerProductView findBestSellerProduct() {
        Optional<BestSellerProductView> optBestSellerProductView = em.createNamedQuery("BestSellerProductView.findAll", BestSellerProductView.class)
                .getResultStream().findFirst();
        return optBestSellerProductView.orElse(null);
    }
    public List<InsolventCustomersReportView> findInsolventCustomersReport() {
        return em.createNamedQuery("InsolventCustomersReportView.findAll", InsolventCustomersReportView.class).getResultList();
    }
    public List<PurchasesPerPackageValidityView> findPurchasePerPackageValidity() {
        return em.createNamedQuery("PurchasesPerPackageValidityView.findAll", PurchasesPerPackageValidityView.class).getResultList();
    }
    public List<PurchasesPerPackageView> findPurchasesPerPackage() {
        return em.createNamedQuery("PurchasesPerPackageView.findAll", PurchasesPerPackageView.class).getResultList();
    }
    public List<TotalSalesValueView> findTotalSalesValue() {
        return em.createNamedQuery("TotalSalesValueView.findAll", TotalSalesValueView.class).getResultList();
    }
}
