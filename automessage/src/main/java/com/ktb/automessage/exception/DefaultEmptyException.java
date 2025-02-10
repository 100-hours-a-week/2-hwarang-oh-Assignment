package com.ktb.automessage.exception;

public class DefaultEmptyException extends Exception {
    public DefaultEmptyException() {
        super("입력값이 비어있습니다!");
    }
}
