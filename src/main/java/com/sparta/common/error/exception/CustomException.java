package com.sparta.common.error.exception;

import com.sparta.common.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST) // response로 들어가는 에러 코드를 400으로 통일
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getErrorMessage(), cause, false, false);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}