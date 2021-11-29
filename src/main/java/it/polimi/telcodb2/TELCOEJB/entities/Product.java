package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "Product")
//@NamedQueries()
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productName", nullable = false)
    private String productName;

    @Column(name = "fee", nullable = false)
    private float fee;

    // Owned (compatibleProduct)
    @OneToMany(mappedBy = "product")
    private ArrayList<CompatibleProduct> compatibleProductArrayList;

    // Owned (chosenProduct)
    @OneToMany(mappedBy = "product")
    private ArrayList<ChosenProduct> chosenProductArrayList;

    public Product(String productName, float fee, ArrayList<CompatibleProduct> compatibleProductArrayList,
                   ArrayList<ChosenProduct> chosenProductArrayList) {
        this.productName = productName;
        this.fee = fee;
        this.compatibleProductArrayList = compatibleProductArrayList;
        this.chosenProductArrayList = chosenProductArrayList;
    }

    public Product() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String name) {
        this.productName = name;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public ArrayList<CompatibleProduct> getCompatibleProductArrayList() {
        return compatibleProductArrayList;
    }

    public void setCompatibleProductArrayList(ArrayList<CompatibleProduct> compatibleProductArrayList) {
        this.compatibleProductArrayList = compatibleProductArrayList;
    }

    public ArrayList<ChosenProduct> getChosenProductArrayList() {
        return chosenProductArrayList;
    }

    public void setChosenProductArrayList(ArrayList<ChosenProduct> chosenProductArrayList) {
        this.chosenProductArrayList = chosenProductArrayList;
    }
}
