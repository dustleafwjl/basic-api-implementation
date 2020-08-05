package com.thoughtworks.rslist.component;

import com.thoughtworks.rslist.exception.Error;
import com.thoughtworks.rslist.exception.RsEventNotValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RsEventExecptionHandler {
    @ExceptionHandler({RsEventNotValidException.class, MethodArgumentNotValidException.class})
    public ResponseEntity RsEventExecptionHandler(HttpServletRequest request, Exception e) {
        Logger logger = LoggerFactory.getLogger(RsEventExecptionHandler.class);
        String errorMessage;
        logger.info("method is "+request.getMethod());
        logger.info("url is "+request.getRequestURI());
        if(e instanceof MethodArgumentNotValidException) {
            errorMessage = "invalid param";
            if(request.getRequestURI().equals("/user") && request.getMethod().equals("POST")) {
                errorMessage = "invalid user";
            }
        }else {
            errorMessage = e.getMessage();
        }
        Error error = new Error();
        error.setError(errorMessage);
        return ResponseEntity.badRequest().body(error);
    }
}
