package it.polimi.telcodb2.TELCOEJB.entities2;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "Schedule", schema = "TelcoDB")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idSchedule", nullable = false)
    private int idSchedule;

    @Column(name="idSchedule", nullable = false)
    private LocalDate activationDate;

    @Column(name="idSchedule", nullable = false)
    private LocalDate deactivationDate;

    // REL: ScheduledProducts
    // Relationship between schedule (owner) and the scheduled products
    @ManyToMany
    @JoinTable(name = "ScheduledProducts",
            joinColumns = @JoinColumn(name = "idSchedule"),
            inverseJoinColumns = @JoinColumn(name = "nameProduct"))
    private Collection<Product> products;

    // REL: ScheduledServices
    // Relationship between schedule (owner) and the scheduled services
    @ManyToMany
    @JoinTable(name = "ScheduledServices",
            joinColumns = @JoinColumn(name = "idSchedule"),
            inverseJoinColumns = @JoinColumn(name = "idService"))
    private Collection<Service> services;

    // TODO: add constructor, getters and setters
}
