package com.coconet.common.errorcode;

public interface ErrorCodeIfs {

    Integer getHttpStatusCode();
    Integer getErrorCode();
    String getErrorDescription();
}
