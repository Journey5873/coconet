package com.coconet.chatservice.common.exceptionhandler;

import com.coconet.chatservice.common.errorcode.ErrorCodeIfs;
import com.coconet.chatservice.common.exception.ApiException;
import com.coconet.chatservice.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Order(value = Integer.MIN_VALUE)
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Response<Object>> apiExceptionHandler(
            ApiException apiException
    ){

        log.error("ApiExceptionHandler", apiException);

        ErrorCodeIfs errorCodeIfs = apiException.getErrorCodeIfs();
        return ResponseEntity
            .status(errorCodeIfs.getHttpStatusCode())
            .body(
                    Response.ERROR(errorCodeIfs.getErrorCode(), apiException.getErrorDescription())
            );

    }
}
