package com.coconet.chatservice.common.errorcode;

public interface ErrorCodeIfs {

    Integer getHttpStatusCode();
    Integer getErrorCode();
    String getErrorDescription();
}
