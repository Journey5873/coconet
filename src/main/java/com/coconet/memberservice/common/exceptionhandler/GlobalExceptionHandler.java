package com.coconet.memberservice.common.exceptionhandler;

import com.coconet.memberservice.common.errorcode.ErrorCode;
import com.coconet.memberservice.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(value = Integer.MAX_VALUE)
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Response<?>> globalExHandler(
            Exception e
    ){
        log.error("", e);

        return ResponseEntity
                .status(500)
                .body(Response.ERROR(ErrorCode.SERVER_ERROR));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Response<?>> validationHandler(Exception e) {
        log.error("Try", e);
        String errorMsg = ((MethodArgumentNotValidException) e).getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity
                .status(400)
                .body(Response.ERROR(400, errorMsg));
    }
}
