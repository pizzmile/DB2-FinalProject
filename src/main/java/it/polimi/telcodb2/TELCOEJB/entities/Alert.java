package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "Alert")
//@NamedQueries()
public class Alert {

    @EmbeddedId
    private AlertPK id; 
    /*
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Owner (alertUser)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    private User user;

    @Column(name = "email", nullable = false)
    private String email;*/ \\ TODO controllare constraints

    @Column(name = "amount", nullable = false)
    private int amount;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    @Temporal(TemporalType.TIME)
    @Column(name = "time", nullable = false)
    private Time time;

    public Alert(AlertPK id, int amount, Date date, Time time) {
        this.id=id;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public Alert() {
    }

    public AlertPK getId(){
        return id;
    }
    
    public void setId(AlertPK id) {
        this.id = id;
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
