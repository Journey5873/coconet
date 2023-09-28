package com.coconet.common.exception;

import com.coconet.common.errorcode.ErrorCodeIfs;

public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCodeIfs();
    String getErrorDescription();
}
