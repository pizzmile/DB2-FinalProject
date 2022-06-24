package it.polimi.telcodb2.EJB.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "Schedule", schema = "TelcoDB")
public class Schedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idSchedule", nullable = false)
    private int idSchedule;

    @Column(name="activationDate", nullable = false)
    private LocalDate activationDate;

    @Column(name="deactivationDate", nullable = false)
    private LocalDate deactivationDate;

    // REL: Has
    // Relationship between a client and its schedules (owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCustomer")
    private Customer customer;

    // REL: ScheduledProducts
    // Relationship between schedule (owner) and the scheduled products
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ScheduledProducts",
            joinColumns = @JoinColumn(name = "idSchedule"),
            inverseJoinColumns = @JoinColumn(name = "idProduct"))
    private Collection<Product> products;

    // REL: ScheduledServices
    // Relationship between schedule (owner) and the scheduled services
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ScheduledServices",
            joinColumns = @JoinColumn(name = "idSchedule"),
            inverseJoinColumns = @JoinColumn(name = "idService"))
    private Collection<Service> services;

    public Schedule() {
    }

    public Schedule(LocalDate activationDate, LocalDate deactivationDate) {
        this.activationDate = activationDate;
        this.deactivationDate = deactivationDate;
    }

    public int getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(int idSchedule) {
        this.idSchedule = idSchedule;
    }

    public LocalDate getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDate activationDate) {
        this.activationDate = activationDate;
    }

    public LocalDate getDeactivationDate() {
        return deactivationDate;
    }

    public void setDeactivationDate(LocalDate deactivationDate) {
        this.deactivationDate = deactivationDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    public Collection<Service> getServices() {
        return services;
    }

    public void setServices(Collection<Service> services) {
        this.services = services;
    }
}
