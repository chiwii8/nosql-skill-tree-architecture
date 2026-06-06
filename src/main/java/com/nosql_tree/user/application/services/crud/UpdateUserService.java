package com.nosql_tree.user.application.services.crud;

import com.nosql_tree.user.domain.exception.UserNotFoundException;
import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.user.domain.ports.inbound.UpdateUserPort;
import com.nosql_tree.user.domain.ports.outbound.UserMongoRepositoryPort;
import com.nosql_tree.user.domain.ports.outbound.UserNeo4jRepositoryPort;
import org.springframework.stereotype.Service;

/**
 * UpdateUserService.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 07/05/2026
 */

//TODO: Update to control the info updated
@Service
public class UpdateUserService implements UpdateUserPort {

    private final UserMongoRepositoryPort userMongoRepositoryPort;
    private final UserNeo4jRepositoryPort userNeo4jRepositoryPort;

    public UpdateUserService(UserMongoRepositoryPort userMongoRepositoryPort, UserNeo4jRepositoryPort userNeo4jRepositoryPort) {
        this.userMongoRepositoryPort = userMongoRepositoryPort;
        this.userNeo4jRepositoryPort = userNeo4jRepositoryPort;
    }

    @Override
    public User updateUser(User user) {
        String userId = user.getId();
        if(userId == null || userId.isBlank())
            throw new IllegalArgumentException("The ID is not valid, maybe null or blank");

        if(!userMongoRepositoryPort.existsById(userId))
            throw new UserNotFoundException("The user is not Found with Id: " + userId);

        userNeo4jRepositoryPort.save(user);
        return userMongoRepositoryPort.updateUser(user);
    }
}
