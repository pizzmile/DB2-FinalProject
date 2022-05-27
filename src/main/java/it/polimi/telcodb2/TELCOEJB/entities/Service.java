package it.polimi.telcodb2.TELCOEJB.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "Service", schema = "TelcoDB", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"type", "minutes", "extraMinutes", "sms", "extraSms", "giga", "extraGiga"})
})
public class Service implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idService", nullable = false)
    private int idService;

    @Column(name="type", nullable = false)
    private String type;

    @Column(name="minutes")
    private int minutes;

    @Column(name="extraMinutes")
    private int extraMinutes;

    @Column(name="sms")
    private int sms;

    @Column(name="extraSms")
    private int extraSms;

    @Column(name="giga")
    private int giga;

    @Column(name="extraGiga")
    private int extraGiga;

    // Relationship between package (owner) and its services
    @ManyToMany(mappedBy = "services", cascade = CascadeType.ALL)
    private Collection<Package> packages;

    // Relationship between schedule (owner) and the scheduled services
    @ManyToMany(mappedBy = "services", cascade = CascadeType.ALL)
    private Collection<Schedule> schedules;

    public Service() {
    }

    public Service(int idService, String type, int minutes, int extraMinutes, int sms, int extraSms, int giga, int extraGiga, Collection<Package> packages, Collection<Schedule> schedules) {
        this.idService = idService;
        this.type = type;
        this.minutes = minutes;
        this.extraMinutes = extraMinutes;
        this.sms = sms;
        this.extraSms = extraSms;
        this.giga = giga;
        this.extraGiga = extraGiga;
        this.packages = packages;
        this.schedules = schedules;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getExtraMinutes() {
        return extraMinutes;
    }

    public void setExtraMinutes(int extraMinutes) {
        this.extraMinutes = extraMinutes;
    }

    public int getSms() {
        return sms;
    }

    public void setSms(int sms) {
        this.sms = sms;
    }

    public int getExtraSms() {
        return extraSms;
    }

    public void setExtraSms(int extraSms) {
        this.extraSms = extraSms;
    }

    public int getGiga() {
        return giga;
    }

    public void setGiga(int giga) {
        this.giga = giga;
    }

    public int getExtraGiga() {
        return extraGiga;
    }

    public void setExtraGiga(int extraGiga) {
        this.extraGiga = extraGiga;
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
