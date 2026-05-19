package com.nosql_tree.skill.application.services.crud;

import com.nosql_tree.skill.domain.exception.SkillHasDependenciesException;
import com.nosql_tree.skill.domain.exception.SkillNotFoundException;
import com.nosql_tree.skill.domain.ports.inbound.DeleteSkillPort;
import com.nosql_tree.skill.domain.ports.outbound.SkillMongoRepositoryPort;
import com.nosql_tree.skill.domain.ports.outbound.SkillNeoRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * DeleteSkillService.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 14/05/2026
 */

@Slf4j
@Service
public class DeleteSkillService implements DeleteSkillPort {

    private final SkillNeoRepositoryPort skillNeoRepositoryPort;
    private final SkillMongoRepositoryPort mongoRepositoryPort;

    public DeleteSkillService(SkillNeoRepositoryPort skillNeoRepositoryPort, SkillMongoRepositoryPort mongoRepositoryPort) {
        this.skillNeoRepositoryPort = skillNeoRepositoryPort;
        this.mongoRepositoryPort = mongoRepositoryPort;
    }

    @Transactional
    @Override
    public void deleteBySlug(String slug) {
        if(slug ==null || slug.isBlank()) {
            log.error("The Slug cannot be null or blank");
            throw new IllegalArgumentException("The Slug cannot be null or blank");
        }

        if(!skillNeoRepositoryPort.existsBySlug(slug)){
            log.error("The Skill cannot be found in the database");
            throw new SkillNotFoundException();
        }

        if(skillNeoRepositoryPort.hasDependencies(slug)){
            log.error("Cannot delete skill '{}' because other skills depends on it",slug);
            throw new SkillHasDependenciesException("Cannot delete a skill that is required by other skills");
        }

        mongoRepositoryPort.deleteBySlug(slug);
        skillNeoRepositoryPort.deleteBySlug(slug);
        log.info("The skill with the slug {} is delete sucessfully",slug);
    }
}
