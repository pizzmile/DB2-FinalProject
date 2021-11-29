package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CompatibleValidityPK implements Serializable {

    @Column(name = "packageId")
    private int packageId;

    @Column(name = "validityId")
    private int validityId;

    public CompatibleValidityPK(int packageId, int validityId) {
        this.packageId = packageId;
        this.validityId = validityId;
    }

    public CompatibleValidityPK() {
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getValidityId() {
        return validityId;
    }

    public void setValidityId(int validityId) {
        this.validityId = validityId;
    }
}
