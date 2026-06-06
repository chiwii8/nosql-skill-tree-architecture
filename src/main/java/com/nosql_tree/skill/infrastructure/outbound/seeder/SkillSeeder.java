package com.nosql_tree.skill.infrastructure.outbound.seeder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosql_tree.skill.application.services.crud.CreateSkillService;
import com.nosql_tree.skill.domain.model.Skill;
import com.nosql_tree.skill.domain.ports.outbound.SkillMongoRepositoryPort;
import com.nosql_tree.skill.domain.ports.outbound.SkillNeoRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SkillSeeder.java
 * <p>
 * Description: This class let load the default values in the data directory skills.json, to have a certain data
 *
 * @author aleja
 * @since 14/05/2026
 */

@Slf4j
@Profile("dev")
@Component
public class SkillSeeder implements CommandLineRunner {

    private final SkillMongoRepositoryPort mongoPort;
    private final SkillNeoRepositoryPort neo4jPort;

    public SkillSeeder(SkillMongoRepositoryPort mongoPort, SkillNeoRepositoryPort neo4jPort) {
        this.mongoPort = mongoPort;
        this.neo4jPort = neo4jPort;
    }

    @NullMarked
    @Override
    public void run(String... args) throws Exception {

        log.info("Starting the seeding process of skills from JSON ...");

        try{
            if(!mongoPort.findAllSkills().isEmpty())
                return;

            Skill javaIntro = new Skill("Java", "Introduccion", "Sintaxis básica y variables", "https://video.url/java1", List.of("https://docs.com"));
            Skill javaPoo = new Skill("Java", "Programacion Orientada a Objetos", "Clases, herencia y polimorfismo", "https://video.url/java2", List.of());
            Skill springBoot = new Skill("Spring", "Programacion Orientada a Objetos", "Creación de APIs REST", "https://video.url/spring1", List.of());

            mongoPort.save(javaIntro);
            neo4jPort.save(javaIntro);

            mongoPort.save(javaPoo);
            neo4jPort.save(javaPoo);

            mongoPort.save(springBoot);
            neo4jPort.save(springBoot);

            log.info("Creando relaciones en el grafo de Neo4j...");

            // POO requiere Introducción
            neo4jPort.addDependency("java-programacion-orientada-a-objetos", "java-introduccion");

            // Spring Boot requiere POO
            neo4jPort.addDependency("spring-programacion-orientada-a-objetos", "java-programacion-orientada-a-objetos");

            log.info("¡Seeder finish successful!.");
        }catch (Exception ex){
            log.error("Seeding skill process failed: {}", ex.getMessage());
        }

    }
}
