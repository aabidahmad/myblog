package com.myblog7.exception;

import com.myblog7.payload.ErrorDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDetails>handleResourceNotFoundException(ResourceNotFound exception,
                                                                       WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(new Date(),
                exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                              WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        // Check if the exception is due to a duplicate entry violation
        if (ex.getMessage().contains("Duplicate entry")) {
            String errorMessage = "Duplicate entry. The record already exists.";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        // Handle other data integrity violation exceptions if needed
        return new ResponseEntity<>("Data integrity violation", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
