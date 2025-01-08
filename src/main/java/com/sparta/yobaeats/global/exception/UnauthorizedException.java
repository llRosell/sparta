package com.sparta.yobaeats.global.exception;

import com.sparta.yobaeats.global.exception.error.ErrorCode;

public class UnauthorizedException extends CustomRuntimeException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
