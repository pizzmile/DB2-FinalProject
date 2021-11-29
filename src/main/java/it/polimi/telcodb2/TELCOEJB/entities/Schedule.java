package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Schedule")
//@NamedQueries()
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Owner (scheduleOrder)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId", referencedColumnName = "orderId", nullable = false)
    private Order order;

    @Temporal(TemporalType.DATE)
    @Column(name = "deactivationDate", nullable = false)
    private Date deactivationDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "activationDate", nullable = false)
    private Date activationDate;


    public Schedule(Order order, Date deactivationDate, Date activationDate) {
        this.order = order;
        this.deactivationDate = deactivationDate;
        this.activationDate = activationDate;
    }

    public Schedule() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Date getDeactivationDate() {
        return deactivationDate;
    }

    public void setDeactivationDate(Date deactivationDate) {
        this.deactivationDate = deactivationDate;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }
}
