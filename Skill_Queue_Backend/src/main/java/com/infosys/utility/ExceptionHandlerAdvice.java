package com.infosys.utility;

import com.infosys.exception.InfyEmployeeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @Autowired
    Environment environment;
    @ExceptionHandler(InfyEmployeeException.class)
    public ResponseEntity<ErrorInfo> exceptionHandler(InfyEmployeeException ex) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMessage(environment.getProperty(ex.getMessage(), "Something went wrong"));
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> exceptionHandler(Exception ex) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMessage(environment.getProperty(ex.getMessage(), "Something went wrong"));
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> exceptionHandler(MethodArgumentNotValidException ex) {
        ErrorInfo errorInfo = new ErrorInfo();
        String errorMsg = ex.getBindingResult().getAllErrors().stream().map(x -> x.getDefaultMessage())
                .collect(Collectors.joining(", "));
        errorInfo.setMessage(errorMsg);
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
