package com.nosql_tree.user.application.services.skill;

import com.nosql_tree.skill.application.services.crud.CreateSkillService;
import com.nosql_tree.skill.domain.ports.outbound.SkillMongoRepositoryPort;
import com.nosql_tree.skill.domain.ports.outbound.SkillNeoRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

/**
 * CreateSkillServiceTest.java
 * <p>
 * Description: This test class verify the next elements of the CreateUserService:
 *  *  <ul>
 *  *      <li> The Skill is Correctly created</li>
 *  *      <li> The body requirement Skill is null, should throw a {@code IllegalArgumentException}</li>
 *  *      <li> The unique identifier slug is already in the db</li>
 *  *       <li> Verify if the prerequisites not exists in db, throw a {@code SkillNotFoundException}</li>
 *  *  </ul>
 * @author aleja
 * @since 28/05/2026
 */

public class CreateSkillServiceTest {
    SkillMongoRepositoryPort skillMongoRepositoryPort;
    SkillNeoRepositoryPort skillNeoRepositoryPort;
    CreateSkillService createSkillService;

    @BeforeEach
    void setUp() {
        skillNeoRepositoryPort = mock(SkillNeoRepositoryPort.class);
        skillMongoRepositoryPort = mock(SkillMongoRepositoryPort.class);
        createSkillService = new CreateSkillService(skillNeoRepositoryPort, skillMongoRepositoryPort);
    }

    @Test
    @DisplayName("Should Create sucessfully the Skill")
    void shouldCreateSkillSucessfully(){

    }

    @Test
    @DisplayName("Should throw a exception for the null required body Skill")
    void shouldThrowExceptionSkillNull(){

    }

    @Test
    @DisplayName("Should throw a exception if the unique identifier already exists")
    void shouldThrowExceptionAlreadyExistsSkill(){

    }

    @Test
    @DisplayName("Should throw a exception if the prerequisites skills is not found")
    void shouldThrowExceptionPreRequirementSkillNotFound(){

    }

}
