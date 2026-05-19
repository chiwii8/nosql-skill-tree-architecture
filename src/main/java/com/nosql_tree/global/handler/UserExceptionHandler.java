package com.nosql_tree.global.handler;

import com.nosql_tree.user.domain.exception.InvalidCredentialException;
import com.nosql_tree.user.domain.exception.UserAlreadyExistsException;
import com.nosql_tree.user.domain.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

/**
 * UserExceptionHandler.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 17/05/2026
 */

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>>  alreadyExists(UserAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String,String>> notFound(UserNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<Map<String,String>> invalidCredentials( InvalidCredentialException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",ex.getMessage()));
    }

}
