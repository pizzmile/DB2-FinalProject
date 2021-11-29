package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "Order")
//@NamedQueries()
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId", nullable = false, unique = true)
    private int orderId;

    @Temporal(TemporalType.DATE)
    @Column(name = "start", nullable = false)
    private Date start;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    @Temporal(TemporalType.TIME)
    @Column(name = "time", nullable = false)
    private Time time;

    @Column(name = "totalCost", nullable = false)
    private float totalCost;

    @Column(name="status", nullable = false)
    private Boolean status;

    // Owner (orderUser)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    private User user;

    // Owner (orderValidity)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "validityId", referencedColumnName = "validityId", nullable = false)
    private Validity validity;

    // Owner (orderPackage)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "packageId", referencedColumnName = "packageId", nullable = false)
    private Package pack;

    // Owned (scheduleOrder)
    @OneToOne(mappedBy = "order")
    private Schedule schedule;

    // Owned (chosenProduct)
    @OneToMany(mappedBy = "order")
    private ArrayList<ChosenProduct> chosenProductArrayList;

    public Order(int orderId, Date start, Date date, Time time, float totalCost, Boolean status, User user,
                 Validity validity, Package pack, Schedule schedule, ArrayList<ChosenProduct> chosenProductArrayList) {
        this.orderId = orderId;
        this.start = start;
        this.date = date;
        this.time = time;
        this.totalCost = totalCost;
        this.status = status;
        this.user = user;
        this.validity = validity;
        this.pack = pack;
        this.schedule = schedule;
        this.chosenProductArrayList = chosenProductArrayList;
    }

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
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

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Validity getValidity() {
        return validity;
    }

    public void setValidity(Validity validity) {
        this.validity = validity;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public ArrayList<ChosenProduct> getChosenProductArrayList() {
        return chosenProductArrayList;
    }

    public void setChosenProductArrayList(ArrayList<ChosenProduct> chosenProductArrayList) {
        this.chosenProductArrayList = chosenProductArrayList;
    }
}
