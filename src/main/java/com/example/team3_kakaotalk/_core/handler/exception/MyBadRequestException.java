package com.example.team3_kakaotalk._core.handler.exception;

import com.example.team3_kakaotalk._core.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;

//유효성 검사 실패, 잘못된 파라메터 요청
@Getter
public class MyBadRequestException extends RuntimeException {
    public MyBadRequestException(String message){super(message);}

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.BAD_REQUEST);
    }

    public HttpStatus status() { return HttpStatus.BAD_REQUEST;}
}
