package com.coconet.common.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthorizationErrorCode implements ErrorCodeIfs{

    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED.value(), 401, "인증 안 됨")
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String errorDescription;
}
