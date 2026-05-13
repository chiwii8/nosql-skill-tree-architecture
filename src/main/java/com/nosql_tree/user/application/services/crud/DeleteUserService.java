package com.nosql_tree.user.application.services.crud;

import com.nosql_tree.user.domain.exception.UserNotFoundException;
import com.nosql_tree.user.domain.ports.inbound.DeleteUserPort;
import com.nosql_tree.user.domain.ports.outbound.UserMongoRepositoryPort;
import com.nosql_tree.user.domain.ports.outbound.UserNeo4jRepositoryPort;
import org.springframework.stereotype.Service;

/**
 * DeleteUserService.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 06/05/2026
 */

@Service
public class DeleteUserService implements DeleteUserPort {

    private final UserMongoRepositoryPort userMongoRepositoryPort;
    private final UserNeo4jRepositoryPort userNeo4jRepositoryPort;

    public DeleteUserService(UserMongoRepositoryPort userMongoRepositoryPort, UserNeo4jRepositoryPort userNeo4jRepositoryPort) {
        this.userMongoRepositoryPort = userMongoRepositoryPort;
        this.userNeo4jRepositoryPort = userNeo4jRepositoryPort;
    }

    @Override
    public void deleteUserById(String id) {
        if(id ==null || id.isBlank())
            throw new IllegalArgumentException("The ID is not valid, maybe null or blank");

        ///Review if the user exists
        if(!userMongoRepositoryPort.existsById(id))
            throw new UserNotFoundException("The user is not Found with Id: " + id);

        userMongoRepositoryPort.deleteById(id);

        userNeo4jRepositoryPort.deleteById(id);


    }
}
