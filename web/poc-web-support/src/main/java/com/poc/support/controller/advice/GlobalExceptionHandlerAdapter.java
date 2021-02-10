package com.poc.support.controller.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poc.support.dto.ApiResponse;
import com.poc.support.exception.InvalidInputException;
import com.poc.support.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandlerAdapter {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    @ResponseBody
    public ResponseEntity<?> invalidInputExceptionHandler(HttpServletRequest request, Exception ex) {
        return ResponseEntity.unprocessableEntity ()
                .body(
                        ApiResponse.of(request, HttpStatus.UNPROCESSABLE_ENTITY)
                                .message(ex.getLocalizedMessage()).build()
                );
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> notFoundExceptionHandler(HttpServletRequest request, Exception ex) {
        return ResponseEntity.unprocessableEntity()
                .body(
                        ApiResponse.of(request, HttpStatus.NOT_FOUND)
                                .message(ex.getLocalizedMessage()).build()
                );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<?> handleMissingServletRequestParameterException(HttpServletRequest request, Exception ex) {
        return ResponseEntity.unprocessableEntity ()
                .body(
                        ApiResponse.of(request, HttpStatus.BAD_REQUEST)
                                .message(ex.getLocalizedMessage()).build()
                );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<?> handleValidationExceptions(HttpServletRequest request,
                                                        MethodArgumentNotValidException ex) throws JsonProcessingException {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.badRequest()
                .body(
                        ApiResponse.of(request, HttpStatus.BAD_REQUEST)
                                .message(ex.getLocalizedMessage())
                                .data(errors).build()
                );
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ResponseEntity<?> handleBadCredentialExceptions(HttpServletRequest request, BadCredentialsException ex) throws JsonProcessingException {
        return ResponseEntity.badRequest ()
                .body(
                        ApiResponse.of(request, HttpStatus.BAD_REQUEST)
                                .message(ex.getLocalizedMessage()).build()
                );
    }
}
