package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AlertPK implements Serializable {

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    public AlertPK(String username, String username) {
        this.username = username;
        this.email = email;
    }

    public AlertPK() {
    }

    public String getUsername() {
        return username;
    }

  \\ TODO Vedere setter per chiavi
    public void setUsername(String username) {
        this.username=username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
