package com.gzhu.interceptor;

import com.gzhu.common.ServerReturnDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ServerReturnDTO exceptionHandler(Exception e)
    {
        e.printStackTrace();
        return ServerReturnDTO.createByFailAndMsg(e.getMessage());
    }

}
