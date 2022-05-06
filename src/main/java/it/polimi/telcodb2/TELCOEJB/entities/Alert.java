package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "Alert")
//@NamedQueries()
public class Alert {

    // TODO chiedere al tutorato
    // Owner (alertUser)
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "username", referencedColumnName = "username")
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false)
    @MapsId("username")
    private User user;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    @Temporal(TemporalType.TIME)
    @Column(name = "time", nullable = false)
    private Time time;

    public Alert(User user, int amount, Date date, Time time) {
        this.user = user;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public Alert() {
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
