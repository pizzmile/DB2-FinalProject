package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CompatibleProductPK implements Serializable {

    @Column(name = "productName")
    private String productName;

    @Column(name = "packageId")
    private int packageId;

    public CompatibleProductPK(String productName, int packageId) {
        this.productName = productName;
        this.packageId = packageId;
    }

    public CompatibleProductPK() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }
}
