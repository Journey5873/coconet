package com.coconet.chatservice.common.exception;

import com.coconet.chatservice.common.errorcode.ErrorCodeIfs;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException implements ApiExceptionIfs {

    private final ErrorCodeIfs errorCodeIfs;
    private final String errorDescription;

    public ApiException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorDescription;
    }
}
