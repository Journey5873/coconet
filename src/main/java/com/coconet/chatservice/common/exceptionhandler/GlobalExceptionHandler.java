package com.coconet.chatservice.common.exceptionhandler;

import com.coconet.chatservice.common.errorcode.ErrorCode;
import com.coconet.chatservice.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
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
}
