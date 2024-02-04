package com.coconet.chatservice.common.exception;

import com.coconet.chatservice.common.errorcode.ErrorCodeIfs;

public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCodeIfs();
    String getErrorDescription();
}
