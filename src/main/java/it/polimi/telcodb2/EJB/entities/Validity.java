package it.polimi.telcodb2.EJB.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Validity", schema = "TelcoDB")
public class Validity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idValidity", nullable = false)
    private int idValidity;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "fee", nullable = false)
    private float fee;

    // Relationship between package (owner) and its compatible validities
    @ManyToMany(mappedBy = "validities", cascade = CascadeType.ALL)
    private Collection<Package> packages;

    // REL: With
    // Relationship between an order (owner) and its included package
    @OneToMany(mappedBy = "validity", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<Order>();

    public Validity() {}
    public Validity(int idValidity, int duration, float fee) {
        this.idValidity = idValidity;
        this.duration = duration;
        this.fee = fee;
    }

    public int getIdValidity() {
        return idValidity;
    }

    public void setIdValidity(int idValidity) {
        this.idValidity = idValidity;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
