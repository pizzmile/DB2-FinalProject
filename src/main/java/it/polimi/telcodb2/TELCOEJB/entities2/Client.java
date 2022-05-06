package it.polimi.telcodb2.TELCOEJB.entities2;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Client", schema = "TelcoDB")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="password", nullable = false)
    private String password;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="solvent", nullable = false)
    private boolean solvent;

    @Column(name="failedPayments", nullable = false)
    private String failedPayments;

    @OneToMany(mappedBy = "Client")
    private List<Product> product;

    // TODO: implement "Trigger" relationship

    public Client() {
    }

    public Client(String password, String email, boolean solvent, String failedPayments, List<Product> product) {
        this.password = password;
        this.email = email;
        this.solvent = solvent;
        this.failedPayments = failedPayments;
        this.product = product;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSolvent() {
        return solvent;
    }

    public void setSolvent(boolean solvent) {
        this.solvent = solvent;
    }

    public String getFailedPayments() {
        return failedPayments;
    }

    public void setFailedPayments(String failedPayments) {
        this.failedPayments = failedPayments;
    }
}
