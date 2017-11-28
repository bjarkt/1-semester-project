package Business;

import Acq.IBooleanMessage;

public class BooleanMessage implements IBooleanMessage {
    private boolean aBoolean;
    private String message;

    public BooleanMessage(boolean aBoolean, String message) {
        this.aBoolean = aBoolean;
        this.message = message;
    }

    public BooleanMessage() {
        this(false, "");
    }

    public boolean getABoolean() {
        return aBoolean;
    }

    public String getMessage() {
        return message;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
