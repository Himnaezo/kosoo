package com.sparta.common.error.exception;

import com.sparta.common.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateUsernameException extends RuntimeException {

    private final ErrorCode errorCode;

    public DuplicateUsernameException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getErrorMessage(), cause, false, false);
        this.errorCode = errorCode;
    }
}
