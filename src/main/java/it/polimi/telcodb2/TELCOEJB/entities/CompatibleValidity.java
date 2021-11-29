package it.polimi.telcodb2.TELCOEJB.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "CompatibleValidity")
//@NamedQueries()
public class CompatibleValidity {

    @EmbeddedId
    private CompatibleValidityPK id;

    @ManyToOne
    @MapsId("validityId")
    @JoinColumn(name = "validityId")
    private Validity validity;

    @ManyToOne
    @MapsId("packageId")
    @JoinColumn(name = "packageId")
    private Package pack;

    public CompatibleValidity(CompatibleValidityPK id, Validity validity, Package pack) {
        this.id = id;
        this.validity = validity;
        this.pack = pack;
    }

    public CompatibleValidity() {
    }

    public CompatibleValidityPK getId() {
        return id;
    }

    public void setId(CompatibleValidityPK id) {
        this.id = id;
    }

    public Validity getValidity() {
        return validity;
    }

    public void setValidity(Validity validity) {
        this.validity = validity;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }
}
