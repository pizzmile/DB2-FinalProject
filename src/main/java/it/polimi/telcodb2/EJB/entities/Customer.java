package it.polimi.telcodb2.EJB.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Customer", schema = "TelcoDB")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="solvent", nullable = false)
    private boolean solvent;

    @Column(name="failedPayments", nullable = false)
    private String failedPayments;

    // REL: Trigger
    // Relationship between an alert (owner) and the customer it refers to
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Alert alert;

    // REL: Create
    // Relationship between an order (owner) and its creator customer
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Collection<Order> orders;

    // REL: Has
    // Relationship between a client and its schedules (owner)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Collection<Schedule> schedules;

    public Customer() {
    }

    public Customer(String username, String password, String email, boolean solvent, String failedPayments, Alert alert, Collection<Order> orders, Collection<Schedule> schedules) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.solvent = solvent;
        this.failedPayments = failedPayments;
        this.alert = alert;
        this.orders = orders;
        this.schedules = schedules;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSolvent() {
        return solvent;
    }

    public void setSolvent(boolean solvent) {
        this.solvent = solvent;
    }

    public String getFailedPayments() {
        return failedPayments;
    }

    public void setFailedPayments(String failedPayments) {
        this.failedPayments = failedPayments;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

    public Collection<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Collection<Schedule> schedules) {
        this.schedules = schedules;
    }
}
