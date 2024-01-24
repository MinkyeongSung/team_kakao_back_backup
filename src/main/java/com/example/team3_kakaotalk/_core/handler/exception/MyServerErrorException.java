package com.example.team3_kakaotalk._core.handler.exception;

import com.example.team3_kakaotalk._core.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;

//서버 에러
@Getter
public class MyServerErrorException extends RuntimeException {
    public MyServerErrorException(String message){super(message);}

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpStatus status() { return HttpStatus.INTERNAL_SERVER_ERROR;}
}
