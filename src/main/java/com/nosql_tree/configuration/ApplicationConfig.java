package com.nosql_tree.configuration;

import com.nosql_tree.user.domain.exception.InvalidCredentialException;
import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.user.domain.ports.outbound.UserMongoRepositoryPort;
import com.nosql_tree.user.infrastructure.outbound.auth.UserDetailsAdapter;
import com.nosql_tree.util.HashPasswordUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * ApplicationConfiguration.java
 * <p>
 * Description: Configuration that implement the identification using the internal Spring interface to establish the correct security with tokens
 *
 * @author aleja
 * @since 08/05/2026
 */

@Configuration
public class ApplicationConfig {

    private final UserMongoRepositoryPort userMongoRepositoryPort;

    public ApplicationConfig(UserMongoRepositoryPort userMongoRepositoryPort) {
        this.userMongoRepositoryPort = userMongoRepositoryPort;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // 1. Buscamos en el dominio puro
            User user = userMongoRepositoryPort.findByEmail(username)
                    .orElseThrow(InvalidCredentialException::new);

            // 2. Lo adaptamos sin tocar la clase User
            return new UserDetailsAdapter(user);
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return HashPasswordUtil.getEncoder();
    }
}
