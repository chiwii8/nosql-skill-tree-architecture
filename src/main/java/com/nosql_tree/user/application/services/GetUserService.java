package com.nosql_tree.user.application.services;

import com.nosql_tree.user.domain.exception.UserNotFoundException;
import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.user.domain.ports.inbound.GetUserPort;
import com.nosql_tree.user.domain.ports.outbound.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ConsultUserUseCase.java
 * <p>
 * Description: Service that realise the function of get a User from the DB
 *
 * @author aleja
 * @since 06/05/2026
 */

@Service
public class GetUserService implements GetUserPort {

    private final UserRepositoryPort userRepositoryPort;

    public GetUserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public User getUserById(String id){

        // Review the argument id state
        if(id==null || id.isBlank())
            throw new IllegalArgumentException("The ID is not valid, maybe null or blank");

        // find the user
        Optional<User> optionalUser = userRepositoryPort.findById(id);
        //review if is in the DB and throw and exception
        if(optionalUser.isEmpty())
            throw new UserNotFoundException("The user is not Found with Id: " + id);

        return optionalUser.get();
    }
}
