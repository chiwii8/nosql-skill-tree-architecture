package com.nosql_tree.skill.application.services.crud;

import com.nosql_tree.skill.domain.exception.SkillDuplicateException;
import com.nosql_tree.skill.domain.exception.SkillHasDependenciesException;
import com.nosql_tree.skill.domain.exception.SkillNotFoundException;
import com.nosql_tree.skill.domain.model.Skill;
import com.nosql_tree.skill.domain.ports.inbound.UpdateSkillPort;
import com.nosql_tree.skill.domain.ports.outbound.SkillMongoRepositoryPort;
import com.nosql_tree.skill.domain.ports.outbound.SkillNeoRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


import java.util.Set;

/**
 * UpdateSkillService.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 17/05/2026
 */

@Slf4j
public class UpdateSkillService  implements UpdateSkillPort {

    private final SkillNeoRepositoryPort skillNeoRepositoryPort;
    private final SkillMongoRepositoryPort mongoRepositoryPort;


    public UpdateSkillService(SkillNeoRepositoryPort skillNeoRepositoryPort, SkillMongoRepositoryPort mongoRepositoryPort) {
        this.skillNeoRepositoryPort = skillNeoRepositoryPort;
        this.mongoRepositoryPort = mongoRepositoryPort;
    }

    @Override
    @Transactional
    public Skill update(String currentSlug, Skill updatedSkill, Set<String> newPrerequisiteSlugs) {

        if (currentSlug == null || currentSlug.isBlank()) {
            log.error("Update failed: current slug is null or blank");
            throw new IllegalArgumentException("Current slug cannot be null or blank");
        }

        skillNeoRepositoryPort.findBySlug(currentSlug)
                .orElseThrow(() -> new SkillNotFoundException("Skill not found: " + currentSlug));


        String newSlug = (updatedSkill.getLabel() + "-" + updatedSkill.getName()).toLowerCase().replace(" ", "-");


        if (!currentSlug.equals(newSlug)) {
            if (skillNeoRepositoryPort.hasDependencies(currentSlug)) {
                log.warn("Blocked update: Cannot change name/label of '{}' because other skills depend on it.", currentSlug);
                throw new SkillHasDependenciesException("Cannot change name or label of a skill that is a prerequisite for other skills.");
            }


            if (skillNeoRepositoryPort.existsBySlug(newSlug)) {
                throw new SkillDuplicateException("A skill with the slug " + newSlug + " already exists.");
            }
        }

        //MongoDB
        if(!currentSlug.equals(newSlug)){
            mongoRepositoryPort.deleteBySlug(currentSlug);
        }
        updatedSkill.setSlug(newSlug);

        mongoRepositoryPort.save(updatedSkill);
        log.debug("Skill data successfully updated in MongoDB");

        Skill savedSkill = skillNeoRepositoryPort.updateSkillAndRelationships(currentSlug, updatedSkill, newPrerequisiteSlugs);
        log.debug("Skill structure successfully updated in Neo4j");


        log.info("Skill '{}' updated successfully. Final Slug: {}", currentSlug,newSlug);

        return savedSkill;
    }
}
