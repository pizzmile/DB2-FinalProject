package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ChosenProduct")
//@NamedQueries()
public class ChosenProduct {

    @EmbeddedId
    private ChosenProductPK id;

    @ManyToOne
    @MapsId("productName")
    @JoinColumn(name = "productName")
    private Product product;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "orderId")
    private Order order;

    public ChosenProduct(ChosenProductPK id, Product product, Order order) {
        this.id = id;
        this.product = product;
        this.order = order;
    }

    public ChosenProduct() {
    }

    public ChosenProductPK getId() {
        return id;
    }

    public void setId(ChosenProductPK id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
