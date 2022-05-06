package it.polimi.telcodb2.TELCOEJB.entities2;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "Service", schema = "TelcoDB")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idService", nullable = false)
    private int idService;

    @Column(name="type", nullable = false)
    private String type;

    @Column(name="minutes")
    private int minutes;

    @Column(name="extraMinutes")
    private int extraMinutes;

    @Column(name="sms")
    private int sms;

    @Column(name="extraSms")
    private int extraSms;

    @Column(name="giga")
    private int giga;

    @Column(name="extraGiga")
    private int extraGiga;

    // Relationship between package (owner) and its services
    @ManyToMany(mappedBy = "services")
    private Collection<Package> packages;

    // Relationship between schedule (owner) and the scheduled services
    @ManyToMany(mappedBy = "services")
    private Collection<Schedule> schedules;

    // TODO: add constructors, setters and getters
}
