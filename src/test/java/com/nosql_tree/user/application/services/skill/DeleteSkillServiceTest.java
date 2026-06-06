package com.nosql_tree.user.application.services.skill;

import com.nosql_tree.skill.application.services.crud.DeleteSkillService;
import com.nosql_tree.skill.domain.ports.outbound.SkillMongoRepositoryPort;
import com.nosql_tree.skill.domain.ports.outbound.SkillNeoRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

/**
 * DeleteSkillServiceTest.java
 * <p>
 * Description: This test class verify the next elements of the DeleteUserService:
 *  *  <ul>
 *  *      <li> The Skill is Correctly deleted</li>
 *  *      <li> The slug requirement Skill is null, should throw a {@code IllegalArgumentException}</li>
 *  *      <li> The unique identifier slug is not found in the db</li>
 *  *       <li> Verify if exists prerequisites in the skill,if exists throw a {@code SkillHasDependenciesException}</li>
 *  *  </ul>
 * @author aleja
 * @since 28/05/2026
 */

public class DeleteSkillServiceTest {
    private SkillNeoRepositoryPort skillNeoRepositoryPort;
    private SkillMongoRepositoryPort skillMongoRepositoryPort;
    private DeleteSkillService deleteSkillService;

    @BeforeEach
    void setUp() {
        skillNeoRepositoryPort = mock(SkillNeoRepositoryPort.class);
        skillMongoRepositoryPort = mock(SkillMongoRepositoryPort.class);
    }

    @Test
    @DisplayName("Should delete successfully the Skill for the slug")
    void shouldDeleteSkillSuccessfully(){

    }

    @Test
    @DisplayName("Should throw an exception cause slug is null or blank")
    void shouldThrowExceptionSkillNull(){

    }

    @Test
    @DisplayName("Should throw an exception if the slug is not found")
    void shouldThrowExceptionSkillNotFound(){}

    @Test
    @DisplayName("Should throw an exception if the skill still have dependencies")
    void shoudlThrowExceptionSkillHasDependencies(){}

}
