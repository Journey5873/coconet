package com.coconet.memberservice.common.exception;

import com.coconet.memberservice.common.errorcode.ErrorCodeIfs;

public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCodeIfs();
    String getErrorDescription();
}
