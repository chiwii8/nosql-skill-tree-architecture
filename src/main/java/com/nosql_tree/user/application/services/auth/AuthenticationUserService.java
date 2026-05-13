package com.nosql_tree.user.application.services;

import com.nosql_tree.user.domain.model.dto.AuthResponse;
import com.nosql_tree.user.domain.model.dto.LoginRequest;
import com.nosql_tree.user.domain.ports.outbound.UserMongoRepositoryPort;
import org.springframework.stereotype.Service;

/**
 * AuthenticationUserService.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 07/05/2026
 */

@Service
public class AuthenticationUserService {
    private final UserMongoRepositoryPort userMongoRepositoryPort;


    public AuthenticationUserService(UserMongoRepositoryPort userMongoRepositoryPort) {
        this.userMongoRepositoryPort = userMongoRepositoryPort;
    }


    public AuthResponse loginRequest(LoginRequest request){
        return new AuthResponse("","","","");
    }
}
