package com.ktb.community.global.exception;

public class ErrorMessage {

    // IMP : 생성 불가능한 유틸리티 클래스
    private ErrorMessage() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * TYPE : [400 BAD REQUEST]
     * ? Server가 Client의 오류를 감지하여, 요청을 처리할 수 없음
     */

    /**
     * TYPE : [401 UNAUTHORIZED]
     * ? Client가 요청한 자원에 대해 유효한 자격 증명이 없음
     */
    public static final String UNAUTHORIZED_MESSAGE = "인증되지 않은 사용자입니다. 🚨";
    public static final String INVALID_USER_MESSAGE = "아이디 혹은 비밀번호가 일치하지 않습니다. 🚨";
    public static final String INVALID_PASSWORD_MESSAGE = "비밀번호가 일치하지 않습니다. 🚨";
    public static final String EXPIRED_ACCESS_TOKEN_MESSAGE = "Access Token이 만료되었습니다. 🚨";
    public static final String EXPIRED_REFRESH_TOKEN_MESSAGE = "Refresh Token이 만료되었습니다. 🚨";

    /**
     * TYPE : [403 FORBIDDEN]
     * ? Client가 요청한 자원에 대한 권한이 없음
     */
    public static final String UNTRUSTWORTHY_TOKEN_MESSAGE = "신뢰할 수 없는 토큰입니다. 🚨";

    /**
     * TYPE : [404 NOT FOUND]
     * ? Client가 요청한 자원은 존재하지 않음
     */
    public static final String NOT_EXIST_USER_MESSAGE = "존재하지 않는 사용자입니다. 🚨";
    public static final String NOT_EXIST_POST_MESSAGE = "존재하지 않는 게시글입니다. 🚨";

    /**
     * TYPE : [409 CONFLICT]
     * ? Client의 요청이 충돌이 발생하여, 요청을 처리할 수 없음
     */
    public static final String DUPLICATED_EMAIL_MESSAGE = "이미 가입된 이메일입니다. 🚨";

    /**
     * TYPE : [500 INTERNAL SERVER ERROR]
     * ? Server에 오류가 발생하여, 요청을 수행할 수 없음
     */
    public static final String SERVER_ERROR_MESSAGE = "서버 오류 발생 🚨";

}
