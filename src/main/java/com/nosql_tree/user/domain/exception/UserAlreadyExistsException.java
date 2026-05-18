package com.nosql_tree.user.domain.exception;

/**
 * UserAlreadyExistsException.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 07/05/2026
 */

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
