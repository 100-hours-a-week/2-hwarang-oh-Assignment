package com.ktb.community.external.jwt.exception;

import com.ktb.community.global.exception.BaseException;
import com.ktb.community.global.exception.ErrorCode;

public class InvalidFormatException extends BaseException {
    public InvalidFormatException() {
        super(ErrorCode.UNTRUSTWORTHY_TOKEN);
    }
}
