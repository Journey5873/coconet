package com.coconet.memberservice.common.response;

import com.coconet.memberservice.common.errorcode.ErrorCode;
import com.coconet.memberservice.common.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {

    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

    public static Result OK() {
        return Result.builder()
                .resultCode(ErrorCode.OK.getErrorCode())
                .resultMessage(ErrorCode.OK.getErrorDescription())
                .resultDescription("OK")
                .build();
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs){
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getErrorDescription())
                .resultDescription("Error")
                .build()
                ;
    }

    // validation용 ERROR
    public static Result ERROR(
            Integer errorCode,
            String errorDescription
    ){
        return Result.builder()
                .resultCode(errorCode)
                .resultMessage(errorDescription)
                .resultDescription("잘못된 요청입니다.")
                .build()
                ;
    }
}
