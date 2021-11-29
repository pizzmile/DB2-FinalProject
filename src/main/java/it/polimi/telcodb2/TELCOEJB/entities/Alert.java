package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "Alert")
//@NamedQueries()
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Owner (alertUser)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    private User user;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    @Temporal(TemporalType.TIME)
    @Column(name = "time", nullable = false)
    private Time time;

    public Alert(User user, String email, int amount, Date date, Time time) {
        this.user = user;
        this.email = email;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public Alert() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
