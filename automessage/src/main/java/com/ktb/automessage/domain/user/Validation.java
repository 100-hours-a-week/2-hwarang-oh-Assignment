package com.ktb.automessage.domain.user;

/**
 * Validation Class는 Contents의 진행 과정에서, 유효성 검사를 위한 Class입니다.
 */
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
