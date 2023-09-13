package com.sparta.common.error;

import com.sparta.common.error.exceptionn.CommonException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CommonException.class)
    public ResponseEntity<String> exception(CommonException e){
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> exception(Exception e){
        return ResponseEntity.status(403).body(e.getMessage());
    }
}
