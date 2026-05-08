package com.nosql_tree.user.domain.exception;

/**
 * UserAlreadyExists.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 07/05/2026
 */

public class UserAlreadyExists extends RuntimeException{

    public UserAlreadyExists(String message) {
        super(message);
    }
}
