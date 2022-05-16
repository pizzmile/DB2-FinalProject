package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "Order", schema = "TelcoDB")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idOrder", nullable = false)
    private int idOrder;

    @Column(name="startDate", nullable = false)
    private LocalDate startDate;

    @Column(name="creationDateTime", nullable = false)
    private LocalDateTime creationDateTime;

    @Column(name="totalCost", nullable = false)
    private float totalCost;

    @Column(name="paid", nullable = false)
    private boolean paid;

    // REL: Create
    // Relationship between an order (owner) and its creator customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usernameCustomer")
    private Customer customer;

    // REL: ChosenProducts
    // Relationship between an order (owner) and the selected additional products
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ChosenProduct",
            joinColumns = @JoinColumn(name = "idOrder"),
            inverseJoinColumns = @JoinColumn(name = "nameProduct"))
    private Collection<Product> products;

    // REL: About
    // Relationship between an order (owner) and its included package
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPackage")
    private Package aPackage;

    public Order() {
    }

    public Order(int idOrder, LocalDate startDate, LocalDateTime creationDateTime, float totalCost, boolean paid, Customer customer, Collection<Product> products, Package aPackage) {
        this.idOrder = idOrder;
        this.startDate = startDate;
        this.creationDateTime = creationDateTime;
        this.totalCost = totalCost;
        this.paid = paid;
        this.customer = customer;
        this.products = products;
        this.aPackage = aPackage;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
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

    public Package getaPackage() {
        return aPackage;
    }

    public void setaPackage(Package aPackage) {
        this.aPackage = aPackage;
    }
}
