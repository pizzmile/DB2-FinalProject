package it.polimi.telcodb2.TELCOEJB.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Alert", schema = "TelcoDB")
public class Alert implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlert", nullable = false)
    private int idAlert;

    @Column(name = "lastPayment", nullable = false)
    private LocalDateTime lastPayment;

    @Column(name = "amount", nullable = false)
    private float amount;

    @Column(name = "email", nullable = false)
    private String email;

    // REL: Trigger
    // Relationship between an alert (owner) and the customer it refers to
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usernameCustomer")
    private Customer customer;

    public Alert() {
    }

    public Alert(int idAlert, LocalDateTime lastPayment, float amount, String email, Customer customer) {
        this.idAlert = idAlert;
        this.lastPayment = lastPayment;
        this.amount = amount;
        this.email = email;
        this.customer = customer;
    }

    public int getIdAlert() {
        return idAlert;
    }

    public void setIdAlert(int idAlert) {
        this.idAlert = idAlert;
    }

    public LocalDateTime getLastPayment() {
        return lastPayment;
    }

    public void setLastPayment(LocalDateTime lastPayment) {
        this.lastPayment = lastPayment;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
