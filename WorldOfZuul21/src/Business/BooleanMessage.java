package Business;

import Acq.IBooleanMessage;

/**
 * A way to return both a string and boolean.
 */
public class BooleanMessage implements IBooleanMessage {
    private boolean aBoolean;
    private String message;

    /**
     * Create a new BooleanMessage from a boolean value and a string.
     *
     * @param aBoolean the boolean value
     * @param message  the string
     */
    public BooleanMessage(boolean aBoolean, String message) {
        this.aBoolean = aBoolean;
        this.message = message;
    }

    /**
     * Create a new BooleanMessage with defaults, false boolean and empty string.
     */
    public BooleanMessage() {
        this(false, "");
    }

    /**
     * Get the boolean.
     *
     * @return a boolean value.
     */
    public boolean getABoolean() {
        return aBoolean;
    }

    /**
     * Get the string.
     *
     * @return a string.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the boolean value.
     *
     * @param aBoolean boolean value.
     */
    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    /**
     * Set the message.
     *
     * @param message message string.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
