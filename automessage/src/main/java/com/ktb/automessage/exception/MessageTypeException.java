package com.ktb.automessage.exception;

public class MessageTypeException extends Exception {
    public MessageTypeException() {
        super("메시지 타입을 입력해 주셔야 합니다!");
    }

    public MessageTypeException(String message) {
        super(message);
    }
}
