package com.nosql_tree.user.infrastructure.inbound;

import com.nosql_tree.user.application.services.auth.AuthenticationUserService;
import com.nosql_tree.user.domain.exception.InvalidCredentialException;
import com.nosql_tree.user.domain.model.dto.AuthResponse;
import com.nosql_tree.user.domain.model.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * AuthenticationController.java
 * <p>
 * Description: Controller that manage the authentication requests from REST
 *
 * @author aleja
 * @since 07/05/2026
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationUserService authenticationUserService;

    public AuthenticationController(AuthenticationUserService authenticationUserService) {
        this.authenticationUserService = authenticationUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        try{
            AuthResponse response = authenticationUserService.loginRequest(request);
            return ResponseEntity.ok(response);
        } catch (InvalidCredentialException e){
            return ResponseEntity.badRequest().build();
        }
    }

    /// TODO: Implement a Blacklist to denied the access the people of have that tokens
    /// Actually only remove the token from the frontend
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(){
        return ResponseEntity.noContent().build();
    }
}
