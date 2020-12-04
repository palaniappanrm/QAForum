package com.eceplatform.QAForum.util;

import com.eceplatform.QAForum.dto.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleAllExceptionMethod(Exception ex, WebRequest requset) {

        ExceptionMessage exceptionMessage = new ExceptionMessage();

        // Handle All Field Validation Errors
        if(ex instanceof MethodArgumentNotValidException) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors();
            for(FieldError fieldError: fieldErrors){
                sb.append(fieldError.getDefaultMessage());
                sb.append(";");
            }
            exceptionMessage.setMessage(sb.toString());
        }else{
            exceptionMessage.setMessage(ex.getLocalizedMessage());
        }

        exceptionMessage.setError(ex.getClass().getCanonicalName());
        exceptionMessage.setPath(((ServletWebRequest) requset).getRequest().getServletPath());

        // return exceptionMessageObj;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionMessage);
    }
}