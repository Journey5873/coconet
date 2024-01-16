package com.coconet.articleservice.common.exception;

import com.coconet.articleservice.common.errorcode.ErrorCodeIfs;

public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCodeIfs();
    String getErrorDescription();
}
