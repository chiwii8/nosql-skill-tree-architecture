package com.nosql_tree.user.application.services.skill;

import com.nosql_tree.skill.application.services.crud.ReadSkillService;
import com.nosql_tree.skill.domain.ports.outbound.SkillMongoRepositoryPort;
import com.nosql_tree.skill.domain.ports.outbound.SkillNeoRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

/**
 * ReadSkillServiceTest.java
 * <p>
 * Description: This test class verify the next elements of the ReadUserService:
 *  *  <ul>
 *  *      <li> The Skill is Correctly read</li>
 *  *      <li> The body requirement String is null, should throw a {@code IllegalArgumentException}</li>
 *  *      <li> The Skill not found,  throw a {@code SkillNotFoundException} </li>
 *  *      <li> Return a Tree Map</li>
 *  *  </ul>
 *
 * @author aleja
 * @since 28/05/2026
 */

public class ReadSkillServiceTest {
    private SkillNeoRepositoryPort skillNeoRepositoryPort;
    private SkillMongoRepositoryPort skillMongoRepositoryPort;

    private ReadSkillService readSkillService;
    @BeforeEach
    void setUp() {
        skillMongoRepositoryPort = mock(SkillMongoRepositoryPort.class);
        skillNeoRepositoryPort = mock(SkillNeoRepositoryPort.class);

        readSkillService = new ReadSkillService(skillNeoRepositoryPort,skillMongoRepositoryPort);
    }

    @Test
    @DisplayName("Should Create Skill Sucessfully")
    void shouldCreateSkillSucessfully(){

    }

    @Test
    @DisplayName("Should throw an error for null or blank slug")
    void shouldThrowSkillNullException(){

    }

    @Test
    @DisplayName("should throw an exception for not found Skill")
    void shouldThrowNotFoundException(){

    }

    @Test
    @DisplayName("should return a Skill tree could be empty if isnt skills, or list of skill")
    void shouldReturnTreeSkill(){

    }
}
