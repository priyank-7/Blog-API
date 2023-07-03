package com.blog.Exceptions;

import com.blog.Payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        return new ResponseEntity<>((new ApiResponse(false, ex.getMessage())), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handelMethodArgNotValidException(MethodArgumentNotValidException ex){
       Map<String,String> resp = new HashMap<>();
       ex.getBindingResult().getAllErrors().forEach((error)->{
           resp.put(((FieldError) error).getField(),error.getDefaultMessage());
       });
       return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException ex){
        return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BasCredentialException.class)
    public ResponseEntity<ApiResponse> BadCredentialExceptionHandler(BadCredentialsException ex){
        return new ResponseEntity<>((new ApiResponse(false, ex.getMessage())), HttpStatus.BAD_REQUEST);
    }
}
