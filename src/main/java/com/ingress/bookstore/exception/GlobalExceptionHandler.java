package com.ingress.bookstore.exception;

import com.ingress.bookstore.exception.types.NotAvailableException;
import com.ingress.bookstore.exception.types.NotFoundException;
import com.ingress.bookstore.models.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleNotAvailableException(NotAvailableException ex) {
        var error = new ErrorResponse(UUID.randomUUID(),
                HttpStatus.CONFLICT,
                ex.getMessage(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        var error = new ErrorResponse(
                UUID.randomUUID(),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        var error = new ErrorResponse(
                UUID.randomUUID(),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<?> handleException(RuntimeException ex) {
//        var error = new ErrorResponse(
//                UUID.randomUUID(),
//                HttpStatus.SEE_OTHER,
//                ex.getMessage(),
//                LocalDateTime.now());
//        return ResponseEntity.status(HttpStatus.SEE_OTHER).body(error);
//    }

}
