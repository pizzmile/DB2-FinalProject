package it.polimi.telcodb2.TELCOEJB.entities2;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "Product", schema = "TelcoDB")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="name", nullable = true)
    private String name;

    @Column(name="fee", nullable = true)
    private float fee;

    // Relationship between package (owner) and its compatible products
    @ManyToMany(mappedBy = "products")
    private Collection<Package> packages;

    // Relationship between schedule (owner) and the scheduled products
    @ManyToMany(mappedBy = "products")
    private Collection<Schedule> schedules;

    // Relationship between an order (owner) and the selected additional products
    @ManyToMany(mappedBy = "products")
    private Collection<Order> orders;

    // TODO: add constructors, setters and getters
}
