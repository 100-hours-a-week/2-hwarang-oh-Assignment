package com.ktb.automessage.exception;

/**
 * MemberNameException Class는 Member의 이름이 null인 경우 발생하는 Exception Class
 */
public class MemberNameException extends Exception {
    public MemberNameException() {
        super("본인의 이름을 입력해 주셔야 합니다!");
    }

    public MemberNameException(String message) {
        super(message);
    }
}
