package com.nosql_tree.user.application.services;

import com.nosql_tree.user.domain.exception.UserAlreadyExists;
import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.user.domain.ports.inbound.CreateUserPort;
import com.nosql_tree.user.domain.ports.outbound.UserRepositoryPort;
import com.nosql_tree.util.HashPasswordUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * CreateUserService.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 06/05/2026
 */

@Service
public class CreateUserService implements CreateUserPort {

    private final UserRepositoryPort userRepositoryPort;

    public CreateUserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public User createUser(User user) {
        if(user == null){
            throw new IllegalArgumentException("The Creation of User cannot be possible with invalid data");
        }

        if(userRepositoryPort.existsByEmail(user.getEmail()))
            throw new UserAlreadyExists("The User email already exists in the database");

        String uuid = UUID.randomUUID().toString();
        String hashedPassword = HashPasswordUtil.encryptPassword(user.getPassword());
        User newUser = new User(
                uuid,
                user.getName(),
                user.getEmail(),
                hashedPassword,
                user.getRole()
        );

        return this.userRepositoryPort.save(newUser);
    }
}
