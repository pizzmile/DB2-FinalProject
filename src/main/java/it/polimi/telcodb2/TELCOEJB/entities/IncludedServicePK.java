package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class IncludedServicePK implements Serializable {

    @Column(name = "serviceId")
    private int serviceId;

    @Column(name = "packageId")
    private int packageId;

    public IncludedServicePK(int serviceId, int packageId) {
        this.serviceId = serviceId;
        this.packageId = packageId;
    }

    public IncludedServicePK() {
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }
}
