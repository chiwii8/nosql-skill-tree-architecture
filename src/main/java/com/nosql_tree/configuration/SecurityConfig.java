package com.nosql_tree.configuration;

import com.nosql_tree.user.infrastructure.outbound.auth.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * SecurityConfig.java
 * <p>
 * Description: This is the implementation of the security configuration
 * Swagger: available for develop and test the applications for all users
 * Security: Establish the requirement of Bearer token to use certain services
 *
 * @author aleja
 * @since 08/05/2026
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                /*.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**",
                                "/api/v3/docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"


                        )
                        .permitAll()
                        .anyRequest().authenticated()
                )*/
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())   /// Configuration Established for the Development and test of the application
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}