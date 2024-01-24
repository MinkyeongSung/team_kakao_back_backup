package com.example.team3_kakaotalk._core.handler.exception;

import com.example.team3_kakaotalk._core.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;

//결과 없음
@Getter
public class MyNotFoundException extends RuntimeException {
    public MyNotFoundException(String message){super(message);}

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.NOT_FOUND);
    }

    public HttpStatus status() { return HttpStatus.NOT_FOUND;}
}
