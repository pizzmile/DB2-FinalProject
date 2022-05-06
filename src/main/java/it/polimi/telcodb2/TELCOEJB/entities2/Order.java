package it.polimi.telcodb2.TELCOEJB.entities2;

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

    @ManyToOne
    @JoinColumn(name = "usernameClient")
    private Client client;

    // REL: ChosenProducts
    // Relationship between an order (owner) and the selected additional products
    @ManyToMany
    @JoinTable(name = "ChosenProducts",
            joinColumns = @JoinColumn(name = "idOrder"),
            inverseJoinColumns = @JoinColumn(name = "nameProduct"))
    private Collection<Product> products;

    // TODO: add constructor, getters, setters
}
