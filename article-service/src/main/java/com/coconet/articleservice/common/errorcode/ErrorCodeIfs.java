package com.coconet.articleservice.common.errorcode;

public interface ErrorCodeIfs {

    Integer getHttpStatusCode();
    Integer getErrorCode();
    String getErrorDescription();
}
