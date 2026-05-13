package com.nosql_tree.user.application.services.auth;

import com.nosql_tree.user.domain.exception.InvalidCredentialException;
import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.user.domain.model.dto.AuthResponse;
import com.nosql_tree.user.domain.model.dto.LoginRequest;
import com.nosql_tree.user.domain.ports.outbound.UserMongoRepositoryPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


/**
 * AuthenticationUserService.java
 * <p>
 * Description: Implementation of the Authentication User using the validation default used in spring.
 * Required the implementation of the UserDetailsService to use correctly the Authentication with the JWT tokens.
 *
 * @author aleja
 * @since 07/05/2026
 */

@Service
public class AuthenticationUserService {

    private final AuthenticationManager authenticationManager;
    private final UserMongoRepositoryPort userMongoRepositoryPort;
    private final JwtService jwtService;

    public AuthenticationUserService(AuthenticationManager authenticationManager, UserMongoRepositoryPort userMongoRepositoryPort, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userMongoRepositoryPort = userMongoRepositoryPort;
        this.jwtService = jwtService;
    }

    public AuthResponse loginRequest(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User loggedUser = userMongoRepositoryPort.findByEmail(request.email())
                .orElseThrow(InvalidCredentialException::new);

        String token = jwtService.generateToken(loggedUser);

        return new AuthResponse(token,loggedUser.getEmail(), loggedUser.getName(), loggedUser.getRole());

    }
}
