package it.polimi.telcodb2.EJB.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Package", schema = "TelcoDB",
        uniqueConstraints = {   // TODO: chiedere se va bene
                @UniqueConstraint(columnNames = {"name", "duration"}),
                @UniqueConstraint(columnNames = {"name", "fee"})
        })
@NamedQueries(
        @NamedQuery(
                name = "Package.findByName",
                query = "SELECT p FROM Package p WHERE p.name = :name"
        )
)
public class Package implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPackage", nullable = false)
    private int idPackage;

    @Column(name = "name", nullable = false)
    private String name;

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
            joinColumns = @JoinColumn(name = "idPackage"),
            inverseJoinColumns = @JoinColumn(name = "idProduct"))
    private Collection<Product> products;

    // REL: CompatibleValidities
    // Relationship between package (owner) and its compatible validities
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "compatibleValidities",
            joinColumns = @JoinColumn(name = "idPackage"),
            inverseJoinColumns = @JoinColumn(name = "idValidity"))
    private Collection<Validity> validities;

    public Package() {
    }

    public Package(String name, List<Validity> validityList) {
        this.name = name;
        this.validities = validityList;
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
