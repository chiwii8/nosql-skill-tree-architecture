package com.nosql_tree.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

/**
 * MongoConfiguration.java
 * <p>
 * Description: This configuration file is created for the failing creation connection with mongoDB without the default configuration of database.
 * Then is just hardcode to config the connection with brute force.
 *
 * @author aleja
 * @since 07/05/2026
 */

@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.database:skilltree_db}")
    private String databaseName;

    @Bean
    public MongoDatabaseFactory mongoDbFactory() {
        // Esto fuerza la conexión a localhost:27017 y a tu DB
        return new SimpleMongoClientDatabaseFactory("mongodb://localhost:27017/" + databaseName);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory factory) {
        return new MongoTemplate(factory);
    }
}