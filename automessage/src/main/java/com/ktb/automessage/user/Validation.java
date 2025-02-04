package com.ktb.automessage.user;

public class Validation {
    String target;
    boolean isValid;

    public Validation() {
        this.target = "";
        this.isValid = false;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getTarget() {
        return this.target;
    }

    public boolean getValid() {
        return this.isValid;
    }
}
