package com.nosql_tree.user.application.services;

import com.nosql_tree.user.application.services.crud.DeleteUserService;
import com.nosql_tree.user.domain.exception.UserNotFoundException;
import com.nosql_tree.user.domain.ports.outbound.UserMongoRepositoryPort;

import com.nosql_tree.user.domain.ports.outbound.UserNeo4jRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * DeleteUserServiceTest.java
 * <p>
 * Description: This test class verify the next elements of the CreateUserService:
 *  *  *  <ul>
 *  *  *      <li> The User is Correctly deleted</li>
 *  *  *      <li> The ID is invalid like null or blank, should throw a {@code IllegalArgumentException}</li>
 *  *  *      <li>> The user is not found in db, should throw a {@code UserNotFoundException}</li>
 *  *  *  </ul>
 *
 * @author aleja
 * @since 07/05/2026
 */

class DeleteUserServiceTest {

    private UserMongoRepositoryPort userMongoRepositoryPort;
    private UserNeo4jRepositoryPort userNeo4jRepositoryPort;
    private DeleteUserService deleteUserService;


    @BeforeEach
    void setUp(){
        userMongoRepositoryPort = mock(UserMongoRepositoryPort.class);
        userNeo4jRepositoryPort = mock(UserNeo4jRepositoryPort.class);

        deleteUserService = new DeleteUserService(userMongoRepositoryPort,userNeo4jRepositoryPort);
    }


    @Test
    @DisplayName("Should delete correctly the user")
    void deleteCorrectUserById(){
        String id = "123e123";

        ///Mocked the verification the exists of the user
        when(userMongoRepositoryPort.existsById(id)).thenReturn(true);

        deleteUserService.deleteUserById(id);

        verify(userMongoRepositoryPort, times(1)).deleteById(id);
        verify(userNeo4jRepositoryPort,times(1)).deleteById(id);
    }


    @Test
    @DisplayName("Should throw a Exception for invalid id")
    void deleteUserByInvalidId(){

        assertThrows(IllegalArgumentException.class, () -> deleteUserService.deleteUserById("  "));
        assertThrows(IllegalArgumentException.class, () -> deleteUserService.deleteUserById(null));
        verify(userMongoRepositoryPort,never()).deleteById(any());
        verify(userNeo4jRepositoryPort,never()).deleteById(any());
    }

    @Test
    @DisplayName("Should throw an Exception for not found user")
    void deleteNotFoundUserById(){
        String fakeId = "123e122";

        ///Mocked the verification of the not found user, so, then return false
        when(userMongoRepositoryPort.existsById(fakeId)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> deleteUserService.deleteUserById(fakeId));

        verify(userMongoRepositoryPort,never()).deleteById(any());
        verify(userNeo4jRepositoryPort,never()).deleteById(any());
    }
}
