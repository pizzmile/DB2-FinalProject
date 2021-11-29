package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "Package")
//@NamedQueries()
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "packageId", nullable = false, unique = true)
    private int packageId;

    @Column(name = "name", nullable = false)
    private String name;

    // Owned (orderPackage)
    @OneToOne(mappedBy = "pack")
    private Order order;

    // Owned (compatibleProduct)
    @OneToMany(mappedBy = "pack")
    private ArrayList<CompatibleProduct> compatibleProductArrayList;

    // Owned (includedService)
    @OneToMany(mappedBy = "pack")
    private ArrayList<IncludedService> includedServiceArrayList;

    // Owned (compatibleValidity)
    @OneToMany(mappedBy = "pack")
    private ArrayList<CompatibleValidity> compatibleValidityArrayList;

    public Package(int packageId, String name, Order order,
                   ArrayList<CompatibleProduct> compatibleProductArrayList,
                   ArrayList<IncludedService> includedServiceArrayList,
                   ArrayList<CompatibleValidity> compatibleValidityArrayList) {
        this.packageId = packageId;
        this.name = name;
        this.order = order;
        this.compatibleProductArrayList = compatibleProductArrayList;
        this.includedServiceArrayList = includedServiceArrayList;
        this.compatibleValidityArrayList = compatibleValidityArrayList;
    }

    public Package() {
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ArrayList<CompatibleProduct> getCompatibleProductArrayList() {
        return compatibleProductArrayList;
    }

    public void setCompatibleProductArrayList(ArrayList<CompatibleProduct> compatibleProductArrayList) {
        this.compatibleProductArrayList = compatibleProductArrayList;
    }

    public ArrayList<IncludedService> getIncludedServiceArrayList() {
        return includedServiceArrayList;
    }

    public void setIncludedServiceArrayList(ArrayList<IncludedService> includedServiceArrayList) {
        this.includedServiceArrayList = includedServiceArrayList;
    }

    public ArrayList<CompatibleValidity> getCompatibleValidityArrayList() {
        return compatibleValidityArrayList;
    }

    public void setCompatibleValidityArrayList(ArrayList<CompatibleValidity> compatibleValidityArrayList) {
        this.compatibleValidityArrayList = compatibleValidityArrayList;
    }
}
