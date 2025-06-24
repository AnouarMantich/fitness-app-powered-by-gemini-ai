package org.app.aiservice.handler;

import org.app.aiservice.exception.ActivityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ActivityNotFoundException.class)
    public ResponseEntity<Object> handleActivityNotFoundException(ActivityNotFoundException ex) {
        Map<String,Object> response = new HashMap<>();
        response.put("message",ex.getMessage());
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

}
