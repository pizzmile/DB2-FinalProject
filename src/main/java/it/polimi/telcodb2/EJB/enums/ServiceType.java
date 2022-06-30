package it.polimi.telcodb2.EJB.enums;

public enum ServiceType {

    FIXED_PHONE     (0),
    FIXED_INTERNET  (1),
    MOBILE_PHONE    (2),
    MOBILE_INTERNET (3);

    private final int code;

    ServiceType(int code) {
        this.code = code;
    }

    private int getCode() {
        return code;
    }
}
