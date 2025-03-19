package com.ktb.community.global.exception;

import lombok.Getter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static com.ktb.community.global.exception.ErrorMessage.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    /**
     * TYPE : [400 BAD REQUEST]
     * ? Server가 Client의 오류를 감지하여, 요청을 처리할 수 없음
     */

    /**
     * TYPE : [401 UNAUTHORIZED]
     * ? Client가 요청한 자원에 대해 유효한 자격 증명이 없음
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, ErrorMessage.UNAUTHORIZED_MESSAGE),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, ErrorMessage.INVALID_PASSWORD_MESSAGE),

    /**
     * TYPE : [403 FORBIDDEN]
     * ? Client가 요청한 자원에 대한 권한이 없음
     */
    UNTRUSTWORTHY_TOKEN_MESSAGE(HttpStatus.FORBIDDEN, ErrorMessage.UNTRUSTWORTHY_TOKEN_MESSAGE),

    /**
     * TYPE : [404 NOT FOUND]
     * ? Client가 요청한 자원은 존재하지 않음
     */
    NOT_EXIST_USER(HttpStatus.NOT_FOUND, ErrorMessage.NOT_EXIST_USER_MESSAGE),
    NOT_EXIST_POST(HttpStatus.NOT_FOUND, ErrorMessage.NOT_EXIST_POST_MESSAGE),

    /**
     * TYPE : [409 CONFLICT]
     * ? Client의 요청이 충돌이 발생하여, 요청을 처리할 수 없음
     */
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, ErrorMessage.DUPLICATED_EMAIL_MESSAGE),

    /**
     * TYPE : [500 INTERNAL SERVER ERROR]
     * ? Server에 오류가 발생하여, 요청을 수행할 수 없음
     */
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.SERVER_ERROR_MESSAGE);

    private final HttpStatus status;
    private final String message;
}
