package com.cj.security.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.cj.security.utils.CommonResult;

/**
 * @Author: CJ
 * @Data: 2020/6/11 10:04
 */
@RestControllerAdvice
public class ExceptionAdapter {

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult exception(Exception e){
        String message = e.getMessage();
        return CommonResult.error().setMessage(message);
    }

}
