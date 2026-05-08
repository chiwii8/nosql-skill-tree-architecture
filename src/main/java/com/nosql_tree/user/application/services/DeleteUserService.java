package com.nosql_tree.user.application.services;

import com.nosql_tree.user.domain.exception.UserNotFoundException;
import com.nosql_tree.user.domain.ports.inbound.DeleteUserPort;
import com.nosql_tree.user.domain.ports.outbound.UserRepositoryPort;
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

    private final UserRepositoryPort userRepositoryPort;

    public DeleteUserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public void deleteUserById(String id) {
        if(id ==null || id.isBlank())
            throw new IllegalArgumentException("The ID is not valid, maybe null or blank");

        ///Review if the user exists
        if(!userRepositoryPort.existsById(id))
            throw new UserNotFoundException("The user is not Found with Id: " + id);

        userRepositoryPort.deleteById(id);
    }
}
