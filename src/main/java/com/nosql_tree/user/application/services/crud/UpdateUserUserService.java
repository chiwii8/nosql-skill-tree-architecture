package com.nosql_tree.user.application.services.crud;

import com.nosql_tree.user.domain.exception.UserNotFoundException;
import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.user.domain.ports.inbound.UpdateUserPort;
import com.nosql_tree.user.domain.ports.outbound.UserMongoRepositoryPort;
import org.springframework.stereotype.Service;

/**
 * UpdateUserService.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 07/05/2026
 */

@Service
public class UpdateUserUserService implements UpdateUserPort {

    private final UserMongoRepositoryPort userMongoRepositoryPort;

    public UpdateUserUserService(UserMongoRepositoryPort userMongoRepositoryPort) {
        this.userMongoRepositoryPort = userMongoRepositoryPort;
    }


    @Override
    public User updateUser(User user) {
        String userId = user.getId();
        if(userId == null || userId.isBlank())
            throw new IllegalArgumentException("The ID is not valid, maybe null or blank");

        if(!userMongoRepositoryPort.existsById(userId))
            throw new UserNotFoundException("The user is not Found with Id: " + userId);

        return userMongoRepositoryPort.updateUser(user);
    }
}
