package it.polimi.telcodb2.EJB.utils.data_handlers;

/**
 * Class to handle validity data
 */
public class ValidityDataHandler {

    private int duration;
    private float fee;

    public ValidityDataHandler(int duration, float fee) {
        this.duration = duration;
        this.fee = fee;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    /**
     * Parse string values into a validity data handler
     * @param duration raw duration string
     * @param fee raw fee string
     * @return the validity data handler
     */
    public static ValidityDataHandler parseValidityData(String duration, String fee) {
        try {
            return new ValidityDataHandler(
                    Integer.parseInt(duration),
                    Float.parseFloat(fee)
            );
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
