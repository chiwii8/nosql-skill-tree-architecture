package com.nosql_tree.global.handler;

import com.nosql_tree.skill.domain.exception.SkillAlreadyExistsException;
import com.nosql_tree.skill.domain.exception.SkillDuplicateException;
import com.nosql_tree.skill.domain.exception.SkillHasDependenciesException;
import com.nosql_tree.skill.domain.exception.SkillNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

/**
 * SkillExceptionHandler.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 18/05/2026
 */

@ControllerAdvice
public class SkillExceptionHandler {

    @ExceptionHandler(SkillNotFoundException.class)
    public ResponseEntity<Map<String,String>> notFound(SkillNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(SkillDuplicateException.class)
    public ResponseEntity<Map<String,String>> duplicate(SkillDuplicateException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(SkillHasDependenciesException.class)
    public ResponseEntity<Map<String,String>> hasDependencies(SkillHasDependenciesException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(SkillAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> alreadyExists(SkillAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",ex.getMessage()));
    }
}
