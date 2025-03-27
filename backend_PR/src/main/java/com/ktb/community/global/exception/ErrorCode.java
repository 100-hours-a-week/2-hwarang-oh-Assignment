package com.ktb.community.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
    INVALID_USER(HttpStatus.UNAUTHORIZED, ErrorMessage.INVALID_USER_MESSAGE),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, ErrorMessage.INVALID_PASSWORD_MESSAGE),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, ErrorMessage.EXPIRED_ACCESS_TOKEN_MESSAGE),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, ErrorMessage.EXPIRED_REFRESH_TOKEN_MESSAGE),

    /**
     * TYPE : [403 FORBIDDEN]
     * ? Client가 요청한 자원에 대한 권한이 없음
     */
    UNTRUSTWORTHY_TOKEN(HttpStatus.FORBIDDEN, ErrorMessage.UNTRUSTWORTHY_TOKEN_MESSAGE),

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
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.SERVER_ERROR_MESSAGE),
    FAIL_TO_ISSUE_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.FAIL_TO_ISSUE_TOKEN_MESSAGE);

    private final HttpStatus status;
    private final String message;
}
