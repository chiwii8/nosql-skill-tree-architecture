package com.nosql_tree.user.domain.exception;

/**
 * UserNotFoundException.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 06/05/2026
 */

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message){
        super(message);

    }
}
