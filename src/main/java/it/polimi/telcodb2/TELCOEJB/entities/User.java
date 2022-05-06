package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "User")
//@NamedQueries()
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "isInsolvent", nullable = false)
    private Boolean status;

    @Column(name = "failedPayments", nullable = false)
    private int failedPayments;

    @OneToOne(mappedBy = "username")
    private Alert alert;    // Owned (alertUser)

    @OneToOne(mappedBy = "user")
    private Order order;    // Owned (orderUser)

    public User(String username, String password, String email, Boolean status, int failedPayments,
                Alert alert, Order order) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
        this.failedPayments = failedPayments;
        this.alert = alert;
        this.order = order;
    }

    public User() {
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getFailedPayments() {
        return failedPayments;
    }

    public void setFailedPayments(int failedPayments) {
        this.failedPayments = failedPayments;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
