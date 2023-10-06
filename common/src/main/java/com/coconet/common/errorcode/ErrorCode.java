package com.coconet.common.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs{

    OK(200, 200, "성공"),
    BAD_REQUEST(400, 400, "잘못된 요청"),
    SERVER_ERROR(500, 500, "서버 내 오류"),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "null point"),
    FORBIDDEN_ERROR(HttpStatus.FORBIDDEN.value(), 401, "권한 없음");
    private final Integer httpStatusCode;   // 클라이언트에 보여줄 status code
    private final Integer errorCode;    // 우리 서버 내부에서 사용할 에러 코드
    private final String errorDescription;  // 에러 설명
}