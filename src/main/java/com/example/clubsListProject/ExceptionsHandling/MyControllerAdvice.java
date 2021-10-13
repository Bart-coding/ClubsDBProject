package com.example.clubsListProject.ExceptionsHandling;

import org.apache.tomcat.jni.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
class NotFoundException extends RuntimeException {

}

@RestControllerAdvice
public class MyControllerAdvice{

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<String> myRuntimeHandler(RuntimeException exception) {
        String message = exception.getMessage();
        if (message.isEmpty())
            return new ResponseEntity<String>("Sorry. Input data is invalid.", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ApiError> constraintViolationExceptionHandler(ConstraintViolationException e) { //błąd walidacji
        List<String> errorDetails = new ArrayList<String>();
        errorDetails.add(e.getMessage());

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Constraint violations",
                errorDetails
        );
        return ResponseEntityBuilder.build(apiError);
        //return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        //błąd walidacji przy użyciu samego(?) @Valid annotation ("Exception to be thrown when validation on an argument annotated with @Valid fails.")
        List<String> errorsDetails;
        errorsDetails = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error->error.getObjectName()+" : "+error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Validation errors",
                errorsDetails
        );

        return ResponseEntityBuilder.build(apiError);
        //return new ResponseEntity<>("You probably don't pass one or more valid properties.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<String> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) { //puste request body
        return new ResponseEntity<>(e.getMessage().split(":")[0], HttpStatus.BAD_REQUEST); //niekonieczne, wystarczy bad request
    }
}
