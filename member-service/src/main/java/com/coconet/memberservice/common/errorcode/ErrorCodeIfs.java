package com.coconet.memberservice.common.errorcode;

public interface ErrorCodeIfs {

    Integer getHttpStatusCode();
    Integer getErrorCode();
    String getErrorDescription();
}
