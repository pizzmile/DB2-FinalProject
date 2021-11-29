package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ChosenProductPK implements Serializable {

    @Column(name = "productName")
    private String productName;

    @Column(name = "orderId")
    private int orderId;

    public ChosenProductPK(String productName, int orderId) {
        this.productName = productName;
        this.orderId = orderId;
    }

    public ChosenProductPK() {
    }

    public String getName() {
        return productName;
    }

    public void setName(String name) {
        this.productName = name;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
