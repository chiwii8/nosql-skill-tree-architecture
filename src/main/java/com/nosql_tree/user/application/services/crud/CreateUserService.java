package com.nosql_tree.user.application.services.crud;

import com.nosql_tree.user.domain.exception.UserAlreadyExists;
import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.user.domain.ports.inbound.CreateUserPort;
import com.nosql_tree.user.domain.ports.outbound.UserMongoRepositoryPort;
import com.nosql_tree.user.domain.ports.outbound.UserNeo4jRepositoryPort;
import com.nosql_tree.util.HashPasswordUtil;
import org.springframework.stereotype.Service;

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

    private final UserMongoRepositoryPort userMongoRepositoryPort;
    private final UserNeo4jRepositoryPort userNeo4jRepositoryPort;

    public CreateUserService(UserMongoRepositoryPort userMongoRepositoryPort, UserNeo4jRepositoryPort userNeo4jRepositoryPort) {
        this.userMongoRepositoryPort = userMongoRepositoryPort;
        this.userNeo4jRepositoryPort = userNeo4jRepositoryPort;
    }

    @Override
    public User createUser(User user) {
        if(user == null){
            throw new IllegalArgumentException("The Creation of User cannot be possible with invalid data");
        }

        if(userMongoRepositoryPort.existsByEmail(user.getEmail()))
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

        User savedUser = this.userMongoRepositoryPort.save(newUser);
        ///Level default value is 1
        this.userNeo4jRepositoryPort.save(newUser);

        return savedUser;
    }
}
