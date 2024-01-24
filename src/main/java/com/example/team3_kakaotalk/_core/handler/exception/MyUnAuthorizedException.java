package com.example.team3_kakaotalk._core.handler.exception;

import com.example.team3_kakaotalk._core.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;

//인증 안됨
@Getter
public class MyUnAuthorizedException extends RuntimeException {
    public MyUnAuthorizedException(String message){super(message);}

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.UNAUTHORIZED);
    }

    public HttpStatus status() { return HttpStatus.UNAUTHORIZED;}
}
