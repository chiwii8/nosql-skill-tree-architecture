package com.nosql_tree.user.application.services.extra;

import com.nosql_tree.skill.domain.exception.SkillNotFoundException;
import com.nosql_tree.skill.domain.model.Skill;
import com.nosql_tree.skill.domain.ports.outbound.SkillNeoRepositoryPort;
import com.nosql_tree.user.domain.exception.UserNotFoundException;
import com.nosql_tree.user.domain.ports.inbound.UserProgressPort;
import com.nosql_tree.user.domain.ports.outbound.UserMongoRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * CompleteSkillService.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 29/05/2026
 */

@Slf4j
@Service
public class CompleteSkillService implements UserProgressPort {

    private final UserMongoRepositoryPort userMongoRepositoryPort;
    private final SkillNeoRepositoryPort skillNeoRepositoryPort;

    public CompleteSkillService(UserMongoRepositoryPort userMongoRepositoryPort, SkillNeoRepositoryPort skillNeoRepositoryPort) {
        this.userMongoRepositoryPort = userMongoRepositoryPort;
        this.skillNeoRepositoryPort = skillNeoRepositoryPort;
    }

    @Override
    @Transactional
    public void addCompleteSkill(String email, String skillSlug) {
        log.info("User {} is attempting to complete skill: {}", email, skillSlug);


        if (!skillNeoRepositoryPort.existsBySlug(skillSlug)) {
            throw new SkillNotFoundException("Skill not found: " + skillSlug);
        }


        Set<String> completedSkills = userMongoRepositoryPort.getCompletedSkills(email);


        if (completedSkills.contains(skillSlug)) {
            log.info("User {} already completed skill {}", email, skillSlug);
            return;
        }

        Set<String> requirements = skillNeoRepositoryPort.findBySlug(skillSlug)
                .map(Skill::getRequirements)
                .orElse(Set.of());


        if (!completedSkills.containsAll(requirements)) {
            log.warn("Security alert: User {} tried to unlock {} without prerequisites", email, skillSlug);
            throw new IllegalArgumentException("Cannot complete skill. Prerequisite requirements not met.");
        }

        userMongoRepositoryPort.addCompletedSkill(email, skillSlug);
        log.info("Skill {} successfully unlocked for user {}", skillSlug, email);
    }

    @Override
    public Set<String> getCompletedSkills(String email) {
        if(email == null || email.isBlank()) {
            log.warn("The email cannot be null or empty");
            throw new IllegalArgumentException("The email cannot be null or empty");
        }

        if(!userMongoRepositoryPort.existsByEmail(email)) {
            log.warn("The user cannot be found in db");
            throw new UserNotFoundException("The user cannot be found in db");
        }

        return  userMongoRepositoryPort.getCompletedSkills(email);

    }
}