package com.example.team3_kakaotalk._core.handler;

import com.example.team3_kakaotalk._core.handler.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestfulExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> badArgument(MethodArgumentNotValidException e){
        BindingResult result = e.getBindingResult();
        List<String> errorList = new ArrayList<>();

        for (FieldError error: result.getFieldErrors()) {
            errorList.add(error.getDefaultMessage());
        }

        return new ResponseEntity<>(errorList, e.getStatusCode());
    }

    @ExceptionHandler(MyBadRequestException.class)
    public ResponseEntity<?> badRequest(MyBadRequestException e) {return new ResponseEntity<>(e.body(), e.status());}
    @ExceptionHandler(MyUnAuthorizedException.class)
    public ResponseEntity<?> unAuthorized(MyUnAuthorizedException e) {return new ResponseEntity<>(e.body(), e.status());}
    @ExceptionHandler(MyNotFoundException.class)
    public ResponseEntity<?> notFound(MyNotFoundException e) {return new ResponseEntity<>(e.body(), e.status());}
    @ExceptionHandler(MyForbiddenException.class)
    public ResponseEntity<?> forbidden(MyForbiddenException e) {return new ResponseEntity<>(e.body(), e.status());}
    @ExceptionHandler(MyServerErrorException.class)
    public ResponseEntity<?> serverError(MyServerErrorException e) {return new ResponseEntity<>(e.body(), e.status());}
}
