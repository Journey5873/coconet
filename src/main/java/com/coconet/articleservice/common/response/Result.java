package com.coconet.articleservice.common.response;

import com.coconet.articleservice.common.errorcode.ErrorCode;
import com.coconet.articleservice.common.errorcode.ErrorCodeIfs;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Result", description = "Response result")
public class Result {

    @Schema(example = "200")
    private Integer resultCode;
    @Schema(example = "OK")
    private String resultMessage;
    @Schema(example = "Everything is okay")
    private String resultDescription;

    public static Result OK() {
        return Result.builder()
                .resultCode(ErrorCode.OK.getErrorCode())
                .resultMessage(ErrorCode.OK.getErrorDescription())
                .resultDescription("OK")
                .build();
    }

    public static Result FOUND() {
        return Result.builder()
                .resultCode(ErrorCode.FOUND.getErrorCode())
                .resultMessage(ErrorCode.FOUND.getErrorDescription())
                .resultDescription("FOUND")
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
