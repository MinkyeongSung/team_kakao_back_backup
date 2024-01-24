package com.example.team3_kakaotalk._core.advice;

import com.example.team3_kakaotalk._core.handler.exception.MyBadRequestException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;

@Aspect
@Component
public class ValidAdvice {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping(){}

    @Before("postMapping()")
    public void checkValid(JoinPoint jp){
        Object[] args = jp.getArgs();

        for (Object arg : args) {
            if (arg instanceof Errors){
                Errors errors = (Errors) arg;

                if(errors.hasErrors()){
                    List<FieldError> fieldErrors = errors.getFieldErrors();
                    throw new MyBadRequestException(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
                }
            }

        }

    }
}
