package it.polimi.telcodb2.TELCOEJB.entities2;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "Package", schema = "TelcoDB",
        uniqueConstraints = {   // TODO: chiedere se va bene
                @UniqueConstraint(columnNames = {"name", "duration"}),
                @UniqueConstraint(columnNames = {"name", "fee"}),
                @UniqueConstraint(columnNames = {"duration", "fee"})
        })
public class Package {

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

    // REL: IncludedServices
    // Relationship between package (owner) and its included services
    @ManyToMany
    @JoinTable(name = "IncludedServices",
            joinColumns = @JoinColumn(name = "namePackage"),
            inverseJoinColumns = @JoinColumn(name = "idService"))
    private Collection<Service> services;

    // REL: CompatibleProducts
    // Relationship between package (owner) and its compatible products
    @ManyToMany
    @JoinTable(name = "CompatibleProducts",
            joinColumns = @JoinColumn(name = "namePackage"),
            inverseJoinColumns = @JoinColumn(name = "nameProduct"))
    private Collection<Product> products;

    // TODO: add constructors, setters and getters
}
