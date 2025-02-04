package com.ktb.automessage.exception;

public class MemberNameException extends Exception {
    public MemberNameException() {
        super("본인의 이름을 입력해 주셔야 합니다!");
    }

    public MemberNameException(String message) {
        super(message);
    }
}
