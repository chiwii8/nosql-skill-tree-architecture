package com.nosql_tree.user.application.services.skill;

import com.nosql_tree.skill.application.services.crud.UpdateSkillService;
import com.nosql_tree.skill.domain.ports.outbound.SkillMongoRepositoryPort;
import com.nosql_tree.skill.domain.ports.outbound.SkillNeoRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

/**
 * UpdateSkillServiceTest.java
 * <p>
 * Description: This test class verify the next elements of the UpdateUserService:
 *   <ul>
 *       <li> The Skill is Correctly updated</li>
 *       <li> The slug requirement Skill is null or blank, should throw a {@code IllegalArgumentException}</li>
 *       <li> The unique identifier slug is not found in the db</li>
 *       <li> If has dependencies to the skill, throw a {@code SkillHasDependenciesException}</li>
 *       <li> The new slug cannot exists, throw a {@code SkillDuplicateException}</li>
 *   </ul>
 * @author aleja
 * @since 28/05/2026
 */

public class UpdateSkillServiceTest {

    private SkillNeoRepositoryPort skillNeoRepositoryPort;
    private SkillMongoRepositoryPort skillMongoRepositoryPort;
    private UpdateSkillService updateSkillService;
    @BeforeEach
    void setUp() {
        skillNeoRepositoryPort = mock(SkillNeoRepositoryPort.class);
        skillMongoRepositoryPort = mock(SkillMongoRepositoryPort.class);

        updateSkillService = new UpdateSkillService(skillNeoRepositoryPort, skillMongoRepositoryPort);
    }

    @Test
    @DisplayName("Should update the Skill successfully")
    void shouldUpdateSkillSuccessfully(){}

    @Test
    @DisplayName("Should throw an exception cause slug is null or blank")
    void shouldThrowExceptionSkillNull(){

    }

    @Test
    @DisplayName("Should throw an exception cause skill is not found")
    void shouldThrowExceptionSKillNotFound(){}

    @Test
    @DisplayName("Should throw an exception cause the actual skill has dependencies")
    void shouldThrowExceptionSkillHasDependencies(){}

    @Test
    @DisplayName("Should throw an exception cause the new slug is already in use ")
    void shoudlThrowExceptionSkillSlugDuplicated(){}
}
