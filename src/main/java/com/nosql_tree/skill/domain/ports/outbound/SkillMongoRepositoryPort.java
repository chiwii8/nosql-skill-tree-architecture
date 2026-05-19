package com.nosql_tree.skill.domain.ports.outbound;

import com.nosql_tree.skill.domain.model.Skill;

import java.util.List;
import java.util.Optional;

public interface SkillMongoRepositoryPort {
    void save(Skill skill);

    Optional<Skill> findBySlug(String slug);

    List<Skill> findAllSkills();

    void deleteBySlug(String slug);
}
