package it.polimi.telcodb2.EJB.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "Package", schema = "TelcoDB",
        uniqueConstraints = {   // TODO: chiedere se va bene
                @UniqueConstraint(columnNames = {"name", "duration"}),
                @UniqueConstraint(columnNames = {"name", "fee"})
        })
public class Package implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPackage", nullable = false)
    private int idPackage;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "fee", nullable = false)
    private float fee;

    // REL: About
    // Relationship between an order (owner) and its included package
    @OneToMany(mappedBy = "aPackage", cascade = CascadeType.ALL)
    private Collection<Order> orders;

    // REL: IncludedServices
    // Relationship between package (owner) and its included services
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "IncludedServices",
            joinColumns = @JoinColumn(name = "namePackage"),
            inverseJoinColumns = @JoinColumn(name = "idService"))
    private Collection<Service> services;

    // REL: CompatibleProducts
    // Relationship between package (owner) and its compatible products
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CompatibleProducts",
            joinColumns = @JoinColumn(name = "namePackage"),
            inverseJoinColumns = @JoinColumn(name = "nameProduct"))
    private Collection<Product> products;

    public Package() {
    }

    public Package(int idPackage, String name, int duration, float fee, Collection<Order> orders, Collection<Service> services, Collection<Product> products) {
        this.idPackage = idPackage;
        this.name = name;
        this.duration = duration;
        this.fee = fee;
        this.orders = orders;
        this.services = services;
        this.products = products;
    }

    public int getIdPackage() {
        return idPackage;
    }

    public void setIdPackage(int idPackage) {
        this.idPackage = idPackage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

    public Collection<Service> getServices() {
        return services;
    }

    public void setServices(Collection<Service> services) {
        this.services = services;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }
}
