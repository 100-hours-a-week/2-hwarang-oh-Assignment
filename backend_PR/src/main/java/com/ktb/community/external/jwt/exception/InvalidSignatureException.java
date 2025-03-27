package com.ktb.community.external.jwt.exception;

import com.ktb.community.global.exception.BaseException;
import com.ktb.community.global.exception.ErrorCode;

public class InvalidSignatureException extends BaseException {
    public InvalidSignatureException() {
        super(ErrorCode.UNTRUSTWORTHY_TOKEN);
    }
}
