package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "IncludedService")
//@NamedQueries()
public class IncludedService {

    @EmbeddedId
    private IncludedServicePK id;

    @ManyToOne
    @MapsId("serviceId")
    @JoinColumn(name = "serviceId")
    private Service service;

    @ManyToOne
    @MapsId("packageId")
    @JoinColumn(name = "packageId")
    private Package pack;

    public IncludedService(IncludedServicePK id, Service service, Package pack) {
        this.id = id;
        this.service = service;
        this.pack = pack;
    }

    public IncludedService() {
    }

    public IncludedServicePK getId() {
        return id;
    }

    public void setId(IncludedServicePK id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }
}
