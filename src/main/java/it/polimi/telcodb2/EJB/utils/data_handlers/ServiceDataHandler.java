package it.polimi.telcodb2.EJB.utils.data_handlers;

import it.polimi.telcodb2.EJB.entities.Service;

/**
 * Class to handle service data
 */
public class ServiceDataHandler {

    private int type;
    private Integer minutes = null;
    private Float extraMinutesFee = null;
    private Integer sms = null;
    private Float extraSmsFee = null;
    private Integer giga = null;
    private Float extraGigaFee = null;

    public ServiceDataHandler(int type) {
        this.type = type;
    }
    public ServiceDataHandler(String type) {
        this.type = Integer.parseInt(type);
    }

    public ServiceDataHandler(int type, int minutes, float extraMinutesFee, int sms, float extraSmsFee, int giga, float extraGigaFee) {
        this.type = type;
        this.minutes = minutes;
        this.extraMinutesFee = extraMinutesFee;
        this.sms = sms;
        this.extraSmsFee = extraSmsFee;
        this.giga = giga;
        this.extraGigaFee = extraGigaFee;
    }
    public ServiceDataHandler(Integer type, Integer minutes, Float extraMinutesFee, Integer sms, Float extraSmsFee, Integer giga, Float extraGigaFee) {
        this.type = type != null ? type : 0;
        this.minutes = minutes != null ? minutes : 0;
        this.extraMinutesFee = extraMinutesFee != null ? extraMinutesFee : 0;
        this.sms = sms != null ? sms : 0;
        this.extraSmsFee = extraSmsFee != null ? extraSmsFee : 0;
        this.giga = giga != null ? giga : 0;
        this.extraGigaFee = extraGigaFee != null ? extraGigaFee : 0;
    }

    public Service toService() {
        return new Service(
                this.type,
                this.minutes, this.extraMinutesFee,
                this.sms, this.extraSmsFee,
                this.giga, this.extraGigaFee);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Float getExtraMinutesFee() {
        return extraMinutesFee;
    }

    public void setExtraMinutesFee(Float extraMinutesFee) {
        this.extraMinutesFee = extraMinutesFee;
    }

    public Integer getSms() {
        return sms;
    }

    public void setSms(Integer sms) {
        this.sms = sms;
    }

    public Float getExtraSmsFee() {
        return extraSmsFee;
    }

    public void setExtraSmsFee(Float extraSmsFee) {
        this.extraSmsFee = extraSmsFee;
    }

    public Integer getGiga() {
        return giga;
    }

    public void setGiga(Integer giga) {
        this.giga = giga;
    }

    public Float getExtraGigaFee() {
        return extraGigaFee;
    }

    public void setExtraGigaFee(Float extraGigaFee) {
        this.extraGigaFee = extraGigaFee;
    }

    public static ServiceDataHandler parseServiceData(String type, String minutes, String extraMinutes, String sms, String extraSms, String giga, String extraGiga) {
        if (type == null || type.isEmpty()) {
            return null;
        }
        switch (type) {
            case "0":
                return new ServiceDataHandler(0, 0, 0, 0, 0, 0, 0);
            case "1":
            case "3":
                if (giga == null || giga.isEmpty() || extraGiga == null || extraGiga.isEmpty()) {
                    return null;
                }
                return new ServiceDataHandler(
                        Integer.parseInt(type), 0, 0, 0, 0, Integer.parseInt(giga), Float.parseFloat(extraGiga)
                );
            case "2":
                if (minutes == null || minutes.isEmpty() || extraMinutes == null || extraMinutes.isEmpty() || sms == null || sms.isEmpty() || extraSms == null || extraSms.isEmpty()) {
                    return null;
                }
                return new ServiceDataHandler(Integer.parseInt(type), Integer.parseInt(minutes), Float.parseFloat(extraMinutes), Integer.parseInt(sms), Float.parseFloat(extraSms), 0, 0);
            default:
                return null;
        }
    }

    /**
     * Check if data contained in the data handler are valid with respect to the business logic
     * @return true if the data are valid, else false
     */
    public boolean isValid() {
        if (type == 0) {
            return minutes == 0 && extraMinutesFee == 0 && sms == 0 && extraSmsFee == 0 && giga == 0 && extraGigaFee == 0;
        } else if (type == 1 || type == 3) {
            return minutes == 0 && extraMinutesFee == 0 && sms == 0 && extraSmsFee == 0 && giga != 0 && extraGigaFee != 0;
        } else if (type == 2) {
            return minutes != 0 && extraMinutesFee != 0 && sms != 0 && extraSmsFee != 0 && giga == 0 && extraGigaFee == 0;
        }
        return false;
    }
}
