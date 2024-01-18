package com.coconet.articleservice.common.response;

import com.coconet.articleservice.common.errorcode.ErrorCodeIfs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Schema(name = "Response", description = "Status with data")
public class Response<T> {

    private Result result;
    @Valid
    private T data;

    public static <T> Response<T> OK(T result){
        Response<T> response = new Response<>();
        response.result = Result.OK();
        response.data = result;
        return response;
    }

    public static Response<Object> ERROR(Result result) {
        Response<Object> response = new Response<>();
//        response.resultCode 는 exHandeler에서 설정해줌
        response.result = result;
        return response;
    }

    public static Response<Object> ERROR(ErrorCodeIfs errorCodeIfs) {
        Response<Object> response = new Response<>();
        response.result = Result.ERROR(errorCodeIfs);
        return response;
    }

    // validation용 ERROR
    public static Response<Object> ERROR(Integer errorCode, String errorDescription) {
        Response<Object> response = new Response<>();
        response.result = Result.ERROR(errorCode, errorDescription);
        return response;
    }


}
