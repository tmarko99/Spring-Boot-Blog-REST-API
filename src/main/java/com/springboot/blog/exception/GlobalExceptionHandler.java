package com.springboot.blog.exception;

import com.springboot.blog.payload.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException exception,
                                                                        WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                                        WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        Map<String, String> errorsList = new HashMap<>();

        body.put("timestamp", System.currentTimeMillis());
        body.put("status", status.value());

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    String fieldName = error.getField();
                    String message = error.getDefaultMessage();
                    errorsList.put(fieldName, message);
                });


        body.put("errors", errorsList);

        return new ResponseEntity<>(body, status);
    }
}

