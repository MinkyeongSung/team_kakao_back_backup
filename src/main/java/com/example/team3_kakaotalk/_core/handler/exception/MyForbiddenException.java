package com.example.team3_kakaotalk._core.handler.exception;

import com.example.team3_kakaotalk._core.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;

//권한 없음
@Getter
public class MyForbiddenException extends RuntimeException {
    public MyForbiddenException(String message){super(message);}

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.FORBIDDEN);
    }

    public HttpStatus status() { return HttpStatus.FORBIDDEN;}
}
