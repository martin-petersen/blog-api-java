package com.example.demo.handler;

import com.example.demo.error.ApiError;
import com.example.demo.exception.ServiceException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HandlerException {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> argumentNotValid(final HttpServletRequest req, final MethodArgumentNotValidException exception) {
        Map<String,String> error = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(objectError -> {
            String fieldName = ((FieldError) objectError).getField();
            String errorMessage = ((FieldError) objectError).getDefaultMessage();
            error.put(fieldName,errorMessage);
        });
        return new ResponseEntity<>(new ApiError(error, HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<ApiError> notFound(final ServiceException e) {
        Map<String,String> error = new HashMap<>();
        error.put(e.getObject(),e.getMessage());
        return new ResponseEntity<>(new ApiError(error,e.getStatus(),e.getStatus().value()),e.getStatus());
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ApiError> notFound(final AuthenticationException e) {
        Map<String,String> error = new HashMap<>();
        error.put("Login","Campos inválidos");
        return new ResponseEntity<>(new ApiError(error,HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }
}
