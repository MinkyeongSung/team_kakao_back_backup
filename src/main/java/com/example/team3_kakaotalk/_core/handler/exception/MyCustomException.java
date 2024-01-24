package com.example.team3_kakaotalk._core.handler.exception;

import org.springframework.http.HttpStatus;

//커스텀 에러
public class MyCustomException extends RuntimeException {
    private HttpStatus httpStatus;

    public MyCustomException(String message) {
        super(message);
    }

    public MyCustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
