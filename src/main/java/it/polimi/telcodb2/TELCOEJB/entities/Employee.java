package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Employee", schema = "TelcoDB")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="username", nullable = false)
    private String username;

    @Column(name="username", nullable = false)
    private String password;

    public Employee() {
    }

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
