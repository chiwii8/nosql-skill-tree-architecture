package com.nosql_tree.user.application.services;

import com.nosql_tree.user.application.services.crud.GetUserService;
import com.nosql_tree.user.domain.exception.UserNotFoundException;
import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.user.domain.ports.outbound.UserMongoRepositoryPort;

/// Config Test
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// JUnit Assertions static imports
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/// Mockito Mock static imports
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.Collections;
import java.util.Optional;

/**
 * GetUserServiceTest.java
 * <p>
 * Description: This test class verify the next elements:
 *  <ul>
 *      <li> The User is Correctly founded</li>
 *      <li> The ID null or Blank should throw a {@code IllegalArgumentException}</li>
 *      <li>> The ID mismatch the user should throw a {@code UserNotFoundException}</li>
 *  </ul>
 *
 *
 * @author aleja
 * @since 07/05/2026
 */


class GetUserServiceTest {
    private UserMongoRepositoryPort userMongoRepositoryPort;
    private GetUserService getUserService;

    @BeforeEach
    void setUp(){
        userMongoRepositoryPort = mock(UserMongoRepositoryPort.class);

        getUserService = new GetUserService(userMongoRepositoryPort);

    }

    @Test
    @DisplayName("Should be return the user if the ID is correct")
    void getCorrectUserById(){

        String user_id = "user-123";
        User expectedUser = new User(user_id,"Pedro", Collections.emptyList());

        /// Set the Mockup Return the expected results in an optional wrapper
        when(userMongoRepositoryPort.findById(user_id))
                .thenReturn(Optional.of(expectedUser));

        /// Should call the Mocked Port
        User resultedUser = getUserService.getUserById(user_id);

        assertNotNull(resultedUser,"The User Expected cannot be null");
        assertEquals(expectedUser.getId(),resultedUser.getId(),"The Id of the Users should be equals");
        assertEquals(expectedUser.getName(),resultedUser.getName(), "The name of the Users should be equals");

        /// This Sentence checks if the MockUp is called one time
        verify(userMongoRepositoryPort, times(1)).findById(user_id);
    }

    @Test
    @DisplayName("Should throw an exception for the Invalid null or blank id")
    void getUserThrowExceptionIdInvalid(){
        String user_id_1 = "";

        /// The Element mocked should not be called in this test

        assertThrows(IllegalArgumentException.class, () -> getUserService.getUserById(user_id_1));
        assertThrows(IllegalArgumentException.class, () -> getUserService.getUserById(null));

        verifyNoInteractions(userMongoRepositoryPort);
    }

    @Test
    @DisplayName("Should Throw an exception of not found user")
    void getUserThrowExceptionWhenNotFound(){
        String user_id = "user-999";

        when(userMongoRepositoryPort.findById(user_id))
                .thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,() -> getUserService.getUserById(user_id));

        String expected_message = "The user is not Found with Id: ".concat(user_id);
        assertEquals(expected_message,exception.getMessage());

        ///The Mockup Should be executed 1 time
        verify(userMongoRepositoryPort,times(1)).findById(user_id);


    }


}
