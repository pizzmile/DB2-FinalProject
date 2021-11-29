package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "CompatibleProduct")
//@NamedQueries()
public class CompatibleProduct {

    @EmbeddedId
    private CompatibleProductPK id;

    @ManyToOne
    @MapsId("productName")
    @JoinColumn(name = "productName")
    private Product product;

    @ManyToOne
    @MapsId("packageId")
    @JoinColumn(name = "packageId")
    private Package pack;

    public CompatibleProduct(CompatibleProductPK id, Product product, Package pack) {
        this.id = id;
        this.product = product;
        this.pack = pack;
    }

    public CompatibleProduct() {
    }

    public CompatibleProductPK getId() {
        return id;
    }

    public void setId(CompatibleProductPK id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }
}
