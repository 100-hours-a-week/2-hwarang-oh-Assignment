package com.ktb.community.external.jwt.exception;

import com.ktb.community.global.exception.BaseException;
import com.ktb.community.global.exception.ErrorCode;

public class ExpiredTokenException extends BaseException {
    public ExpiredTokenException() {
        super(ErrorCode.EXPIRED_ACCESS_TOKEN);
    }
}
