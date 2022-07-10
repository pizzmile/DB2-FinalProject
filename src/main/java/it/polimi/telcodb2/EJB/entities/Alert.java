package it.polimi.telcodb2.EJB.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Alert", schema = "TelcoDB")
@NamedQueries(
        {
                @NamedQuery(
                        name = "Alert.findByCustomerId",
                        query = "SELECT a FROM Alert a WHERE a.customer.idCustomer = :idCustomer"
                )
        }
)
public class Alert implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlert", nullable = false)
    private int idAlert;

    @Column(name = "lastPayment", nullable = false)
    private LocalDateTime lastPayment;

    @Column(name = "amount", nullable = false)
    private float amount;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    // REL: Trigger
    // Relationship between an alert (owner) and the customer it refers to
    @OneToOne
    @JoinColumn(name = "idCustomer")
    private Customer customer;

    public Alert() {
    }

    public Alert(LocalDateTime lastPayment, float amount, String email, String username, Customer customer) {
        this.lastPayment = lastPayment;
        this.amount = amount;
        this.email = email;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
