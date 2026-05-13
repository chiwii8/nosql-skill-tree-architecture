package com.nosql_tree.user.application.services;

import com.nosql_tree.user.application.services.crud.CreateUserService;
import com.nosql_tree.user.domain.exception.UserAlreadyExists;
import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.user.domain.ports.outbound.UserMongoRepositoryPort;
import com.nosql_tree.user.domain.ports.outbound.UserNeo4jRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * CreateUserServiceTest.java
 * <p>
 * Description: This test class verify the next elements of the CreateUserService:
 *  *  <ul>
 *  *      <li> The User is Correctly created</li>
 *  *      <li> The body requirement User is null, should throw a {@code IllegalArgumentException}</li>
 *  *      <li>> The unique identifier email is already in the db</li>
 *  *  </ul>
 *
 * @author aleja
 * @since 07/05/2026
 */

class CreateUserServiceTest {
    private UserMongoRepositoryPort userMongoRepositoryPort;
    private UserNeo4jRepositoryPort userNeo4jRepositoryPort;
    private CreateUserService createUserService;


    @BeforeEach
    void setUp(){
        userMongoRepositoryPort = mock(UserMongoRepositoryPort.class);
        userNeo4jRepositoryPort = mock(UserNeo4jRepositoryPort.class);
        createUserService = new CreateUserService(userMongoRepositoryPort,userNeo4jRepositoryPort);
    }


    @Test
    @DisplayName("Should be return the user with a generated ID")
    void createCorrectUser(){
        User testUser = new User("Pedro");

        when(userMongoRepositoryPort.save(any(User.class)))
                .thenAnswer(i ->{
                    User user = i.getArgument(0);
                    user.setId(UUID.randomUUID().toString());
                    return user;
                });

        when(userMongoRepositoryPort.existsByEmail(testUser.getEmail())).thenReturn(false);

        User created = createUserService.createUser(testUser);

        assertNotNull(created.getId());

        verify(userMongoRepositoryPort,times(1)).save(any(User.class));
        verify(userNeo4jRepositoryPort,times(1)).save(any(User.class));

    }

    @Test
    @DisplayName("Should throw an exception for invalid user argument")
    void createInvalidUser(){
        assertThrows(IllegalArgumentException.class, () -> createUserService.createUser(null));
        verify(userMongoRepositoryPort,never()).save(any(User.class));
        verify(userNeo4jRepositoryPort,never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw an exception for an existing mail")
    void createAlreadyExistsUserEmail(){
        User testUser = new User("Pedro");

        ///Mocked the save repositoryPort method to save correctly the user
        when(userMongoRepositoryPort.save(any(User.class))).thenReturn(testUser);

        ///Mocked the first creations not exists the email
        when(userMongoRepositoryPort.existsByEmail(testUser.getEmail())).thenReturn(false);

        User createdUser = createUserService.createUser(testUser);
        assertNotNull(createdUser,"The User Created cannot return like a null");

        ///Mocked the second creation the email already exists
        when(userMongoRepositoryPort.existsByEmail(testUser.getEmail())).thenReturn(true);

        ///Try to create the same user with the same email
        assertThrows(UserAlreadyExists.class, () -> createUserService.createUser(testUser));

        /// Verify is executed only 1 times for created the first user
        verify(userMongoRepositoryPort,times(1)).save(any(User.class));
        verify(userNeo4jRepositoryPort,times(1)).save(any(User.class));
    }
}
