package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "Validity")
//@NamedQueries()
public class Validity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "validityId", nullable = false, unique = true)
    private int validityId;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "fee", nullable = false)
    private float fee;

    // Owned (orderValidity)
    @OneToOne(mappedBy = "validity")
    private Order order;

    // Owned (compatibleValidity)
    @OneToMany(mappedBy = "validity")
    private ArrayList<CompatibleValidity> compatibleValidityArrayList;

    public Validity(int validityId, int duration, float fee, Order order,
                    ArrayList<CompatibleValidity> compatibleValidityArrayList) {
        this.validityId = validityId;
        this.duration = duration;
        this.fee = fee;
        this.order = order;
        this.compatibleValidityArrayList = compatibleValidityArrayList;
    }

    public Validity() {
    }

    public int getValidityId() {
        return validityId;
    }

    public void setValidityId(int validityId) {
        this.validityId = validityId;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ArrayList<CompatibleValidity> getCompatibleValidityArrayList() {
        return compatibleValidityArrayList;
    }

    public void setCompatibleValidityArrayList(ArrayList<CompatibleValidity> compatibleValidityArrayList) {
        this.compatibleValidityArrayList = compatibleValidityArrayList;
    }
}
