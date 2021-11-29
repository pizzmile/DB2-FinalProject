package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Service")
//@NamedQueries()
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serviceId", nullable = false, unique = true)
    private int serviceId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "minutes", nullable = true)
    private int minutes;

    @Column(name = "extra-minutes", nullable = true)
    private int extraMinutes;

    @Column(name = "sms", nullable = true)
    private int sms;

    @Column(name = "extra-sms", nullable = true)
    private int extraSms;

    @Column(name = "giga", nullable = true)
    private int giga;

    @Column(name = "extra-giga", nullable = true)
    private int extraGiga;

    public Service(int serviceId, String type, int minutes, int extraMinutes, int sms, int extraSms, int giga, int extraGiga) {
        this.serviceId = serviceId;
        this.type = type;
        this.minutes = minutes;
        this.extraMinutes = extraMinutes;
        this.sms = sms;
        this.extraSms = extraSms;
        this.giga = giga;
        this.extraGiga = extraGiga;
    }

    public Service() {}

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
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
}
