package com.ktb.community.external.jwt.exception;

import com.ktb.community.global.exception.BaseException;
import com.ktb.community.global.exception.ErrorCode;

public class FailToIssueTokenException extends BaseException {
    public FailToIssueTokenException() {
        super(ErrorCode.FAIL_TO_ISSUE_TOKEN);
    }

}
