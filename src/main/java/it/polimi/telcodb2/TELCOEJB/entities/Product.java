package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "Product", schema = "TelcoDB")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="name", nullable = true)
    private String name;

    @Column(name="fee", nullable = true)
    private float fee;

    // Relationship between package (owner) and its compatible products
    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private Collection<Package> packages;

    // Relationship between schedule (owner) and the scheduled products
    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private Collection<Schedule> schedules;

    // Relationship between an order (owner) and the selected additional products
    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private Collection<Order> orders;

    public Product() {
    }

    public Product(String name, float fee, Collection<Package> packages, Collection<Schedule> schedules, Collection<Order> orders) {
        this.name = name;
        this.fee = fee;
        this.packages = packages;
        this.schedules = schedules;
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public Collection<Package> getPackages() {
        return packages;
    }

    public void setPackages(Collection<Package> packages) {
        this.packages = packages;
    }

    public Collection<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Collection<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }
}
