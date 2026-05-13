package com.nosql_tree.user.domain.exception;

/**
 * InvalidCredentialException.java
 * <p>
 * Description: Exception used for the bad use of the credentials when is trying to log in the account, could be email or password
 *
 * @author aleja
 * @since 08/05/2026
 */

public class InvalidCredentialException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Cannot log in, Bad Credentials";

    public InvalidCredentialException(String message){
        super(message);
    }

    public InvalidCredentialException(){
        super(DEFAULT_MESSAGE);
    }
}
