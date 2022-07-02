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

    public ServiceDataHandler(int type, int minutes, float extraMinutesFee, int sms, float extraSmsFee) {
        this.type = type;
        this.minutes = minutes;
        this.extraMinutesFee = extraMinutesFee;
        this.sms = sms;
        this.extraSmsFee = extraSmsFee;
    }

    public ServiceDataHandler(int type, int giga, float extraGigaFee) {
        this.type = type;
        this.giga = giga;
        this.extraGigaFee = extraGigaFee;
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

    /**
     * Parse string values into a service data handler
     * @param type raw type string
     * @param minutes raw minutes string
     * @param extraMinutesFee raw extra minutes fee string
     * @param sms raw sms string
     * @param extraSmsFee raw extra sms fee string
     * @return the service data handler
     */
    public static ServiceDataHandler parseServiceData(String type, String minutes, String extraMinutesFee, String sms, String extraSmsFee) {
        return new ServiceDataHandler(
                Integer.parseInt(type),
                Integer.parseInt(minutes),
                Float.parseFloat(extraMinutesFee),
                Integer.parseInt(sms),
                Float.parseFloat(extraSmsFee)
        );
    }

    /**
     * Parse string values into a service data handler
     * @param type raw type string
     * @param giga raw giga string
     * @param extraGigaFee raw extra giga fee string
     * @return the service data handler
     */
    public static ServiceDataHandler parseServiceData(String type, String giga, String extraGigaFee) {
        return new ServiceDataHandler(
                Integer.parseInt(type),
                Integer.parseInt(giga),
                Float.parseFloat(extraGigaFee)
        );
    }

    /**
     * Parse string values into a service data handler
     * @param type raw type string
     * @return the service data handler
     */
    public static ServiceDataHandler parseServiceData(String type) {
        return new ServiceDataHandler(
                Integer.parseInt(type)
        );
    }
}
