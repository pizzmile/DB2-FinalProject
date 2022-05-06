package it.polimi.telcodb2.TELCOEJB.entities2;

import jakarta.persistence.*;
import jdk.vm.ci.meta.Local;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Table(name = "Alert", schema = "TelcoDB")
public class Alert {

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

    public Alert() {
    }

    public Alert(int idAlert, LocalDateTime lastPayment, float amount, String email) {
        this.idAlert = idAlert;
        this.lastPayment = lastPayment;
        this.amount = amount;
        this.email = email;
    }

    public int getIdEntity() {
        return idAlert;
    }

    public void setIdEntity(int idAlert) {
        this.idAlert = idAlert;
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
}
