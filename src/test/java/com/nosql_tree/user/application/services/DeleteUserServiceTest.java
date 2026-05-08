package com.nosql_tree.user.application.services;

import com.nosql_tree.user.domain.exception.UserNotFoundException;
import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.user.domain.ports.outbound.UserRepositoryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.Collections;
import java.util.Optional;

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

    private UserRepositoryPort userRepositoryPort;
    private DeleteUserService deleteUserService;


    @BeforeEach
    void setUp(){
        userRepositoryPort = mock(UserRepositoryPort.class);

        deleteUserService = new DeleteUserService(userRepositoryPort);
    }


    @Test
    @DisplayName("Should delete correctly the user")
    void deleteCorrectUserById(){
        String id = "123e123";

        ///Mocked the verification the exists of the user
        when(userRepositoryPort.existsById(id)).thenReturn(true);

        deleteUserService.deleteUserById(id);

        verify(userRepositoryPort, times(1)).deleteById(id);
    }


    @Test
    @DisplayName("Should throw a Exception for invalid id")
    void deleteUserByInvalidId(){

        assertThrows(IllegalArgumentException.class, () -> deleteUserService.deleteUserById("  "));
        assertThrows(IllegalArgumentException.class, () -> deleteUserService.deleteUserById(null));
        verify(userRepositoryPort,never()).deleteById(any());
    }

    @Test
    @DisplayName("Should throw an Exception for not found user")
    void deleteNotFoundUserById(){
        String fakeId = "123e122";

        ///Mocked the verification of the not found user, so, then return false
        when(userRepositoryPort.existsById(fakeId)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> deleteUserService.deleteUserById(fakeId));

        verify(userRepositoryPort,never()).deleteById(any());
    }
}
