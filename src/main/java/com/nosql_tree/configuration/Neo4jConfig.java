package com.nosql_tree.configuration;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Neo4jConfig.java
 * <p>
 * Description: Configuration of neo4j driver.
 *
 * @author aleja
 * @since 07/05/2026
 */

@Configuration
public class Neo4jConfig {

    @Value("${spring.data.neo4j.uri:bolt://localhost:7687}")
    private String neo4jUri;

    @Value("${spring.data.neo4j.authentication.username:neo4j}")
    private String username;

    @Value("${spring.data.neo4j.authentication.password:password123}")
    private String password;

    @Bean
    public Driver neo4jDriver(){
        return GraphDatabase.driver(neo4jUri, AuthTokens.basic(username, password));
    }
}
