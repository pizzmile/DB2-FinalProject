package it.polimi.telcodb2.EJB.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Package", schema = "TelcoDB")
@NamedQueries(
        {
                @NamedQuery(
                        name = "Package.findByName",
                        query = "SELECT p FROM Package p WHERE p.name = :name"
                ),
                @NamedQuery(
                        name = "Package.findAll",
                        query = "SELECT p FROM Package p"
                )
        }
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
    private List<Order> orders;

    // REL: IncludedServices
    // Relationship between package (owner) and its included services
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "IncludedServices", schema = "TelcoDB",
            joinColumns = @JoinColumn(name = "idPackage"),
            inverseJoinColumns = @JoinColumn(name = "idService"))
    private List<Service> services = new ArrayList<>();

    // REL: CompatibleProducts
    // Relationship between package (owner) and its compatible products
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "CompatibleProducts", schema = "TelcoDB",
            joinColumns = @JoinColumn(name = "idPackage"),
            inverseJoinColumns = @JoinColumn(name = "idProduct"))
    private List<Product> products = new ArrayList<>();

    // REL: CompatibleValidities
    // Relationship between package (owner) and its compatible validities
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "compatibleValidities", schema = "TelcoDB",
            joinColumns = @JoinColumn(name = "idPackage"),
            inverseJoinColumns = @JoinColumn(name = "idValidity"))
    private List<Validity> validities = new ArrayList<>();

    public Package() {
    }

    public Package(String name, List<Validity> validityList, List<Service> serviceList, List<Product> productList) {
        this.name = name;
        this.validities = validityList;
        this.services = serviceList;
        this.products = productList;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Validity> getValidities() {
        return validities;
    }

    public void setValidities(List<Validity> validities) {
        this.validities = validities;
    }
}
