package it.polimi.telcodb2.EJB.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "Service", schema = "TelcoDB", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"serviceType", "minutes", "extraMinutesFee", "sms", "extraSmsFee", "giga", "extraGigaFee"})
})
@NamedQueries(
        {
                @NamedQuery(
                        name = "Service.findEquivalent",
                        query = "SELECT s FROM Service s WHERE s.serviceType = :serviceType " +
                                "AND s.minutes = :minutes AND s.extraMinutesFee = :extraMinutesFee " +
                                "AND s.sms = :sms AND s.extraSmsFee = :extraSmsFee " +
                                "AND s.giga = :giga AND s.extraGigaFee = :extraGigaFee"
                ),
                @NamedQuery(
                        name = "Service.findAll",
                        query = "SELECT s FROM Service s"
                )
        }
)
public class Service implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idService", nullable = false)
    private int idService;

    @Column(name= "serviceType", nullable = false)
    private int serviceType;

    @Column(name="minutes")
    private int minutes = 0;

    @Column(name="extraMinutesFee")
    private float extraMinutesFee = 0;

    @Column(name="sms")
    private int sms = 0;

    @Column(name="extraSmsFee")
    private float extraSmsFee = 0;

    @Column(name="giga")
    private int giga = 0;

    @Column(name="extraGigaFee")
    private float extraGigaFee = 0;

    // Relationship between package (owner) and its services
    @ManyToMany(mappedBy = "services", cascade = CascadeType.ALL)
    private Collection<Package> packages;

    // Relationship between schedule (owner) and the scheduled services
    @ManyToMany(mappedBy = "services", cascade = CascadeType.ALL)
    private Collection<Schedule> schedules;

    public Service() {
    }

    public Service(int serviceType, Integer minutes, Float extraMinutesFee, Integer sms, Float extraSmsFee, Integer giga, Float extraGigaFee) {
        this.serviceType = serviceType;
        this.minutes = minutes != null ? minutes : 0;
        this.extraMinutesFee = extraMinutesFee!= null ? extraMinutesFee : 0;
        this.sms = sms != null ? sms : 0;
        this.extraSmsFee = extraSmsFee != null ? extraSmsFee : 0;
        this.giga = giga != null ? giga : 0;
        this.extraGigaFee = extraGigaFee != null ? extraGigaFee : 0;
    }
    public Service(int serviceType) {
        this.serviceType = serviceType;
    }
    public Service(int serviceType, int minutes, float extraMinutesFee, int sms, float extraSmsFee) {
        this.serviceType = serviceType;
        this.minutes = minutes;
        this.extraMinutesFee = extraMinutesFee;
        this.sms = sms;
        this.extraSmsFee = extraSmsFee;
    }
    public Service(int serviceType, int giga, float extraGigaFee) {
        this.serviceType = serviceType;
        this.giga = giga;
        this.extraGigaFee = extraGigaFee;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
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
