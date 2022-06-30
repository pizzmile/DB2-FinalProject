package it.polimi.telcodb2.EJB.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "Service", schema = "TelcoDB", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"type", "minutes", "extraMinutesFee", "sms", "extraSmsFee", "giga", "extraGigaFee"})
})
public class Service implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idService", nullable = false)
    private int idService;

    @Column(name="type", nullable = false)
    private int type;

    @Column(name="minutes")
    private int minutes;

    @Column(name="extraMinutesFee")
    private float extraMinutesFee;

    @Column(name="sms")
    private int sms;

    @Column(name="extraSmsFee")
    private float extraSmsFee;

    @Column(name="giga")
    private int giga;

    @Column(name="extraGigaFee")
    private float extraGigaFee;

    // Relationship between package (owner) and its services
    @ManyToMany(mappedBy = "services", cascade = CascadeType.ALL)
    private Collection<Package> packages;

    // Relationship between schedule (owner) and the scheduled services
    @ManyToMany(mappedBy = "services", cascade = CascadeType.ALL)
    private Collection<Schedule> schedules;

    public Service() {
    }

    public Service(int type, int minutes, float extraMinutesFee, int sms, float extraSmsFee, int giga, float extraGigaFee) {
        this.type = type;
        this.minutes = minutes;
        this.extraMinutesFee = extraMinutesFee;
        this.sms = sms;
        this.extraSmsFee = extraSmsFee;
        this.giga = giga;
        this.extraGigaFee = extraGigaFee;
    }
    public Service(int type) {
        this.type = type;
    }
    public Service(int type, int minutes, float extraMinutesFee, int sms, float extraSmsFee) {
        this.type = type;
        this.minutes = minutes;
        this.extraMinutesFee = extraMinutesFee;
        this.sms = sms;
        this.extraSmsFee = extraSmsFee;
    }
    public Service(int type, int giga, float extraGigaFee) {
        this.type = type;
        this.giga = giga;
        this.extraGigaFee = extraGigaFee;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public float getExtraMinutesFee() {
        return extraMinutesFee;
    }

    public void setExtraMinutesFee(float extraMinutesFee) {
        this.extraMinutesFee = extraMinutesFee;
    }

    public int getSms() {
        return sms;
    }

    public void setSms(int sms) {
        this.sms = sms;
    }

    public float getExtraSmsFee() {
        return extraSmsFee;
    }

    public void setExtraSmsFee(float extraSmsFee) {
        this.extraSmsFee = extraSmsFee;
    }

    public int getGiga() {
        return giga;
    }

    public void setGiga(int giga) {
        this.giga = giga;
    }

    public float getExtraGigaFee() {return extraGigaFee;}

    public void setExtraGigaFee(float extraGigaFee) {
        this.extraGigaFee = extraGigaFee;
    }

    public Collection<Package> getPackages() {
        return packages;
    }

    public void setPackages(Collection<Package> packages) {
        this.packages = packages;
    }

    public Collection<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Collection<Schedule> schedules) {
        this.schedules = schedules;
    }
}
