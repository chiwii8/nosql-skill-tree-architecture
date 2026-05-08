package com.nosql_tree.skill.domain.ports.outbound;

import com.nosql_tree.skill.domain.model.Skill;
import java.util.Optional;

interface SkillRepositoryPort {
    void save(Skill skill);
    Optional<Skill> searchById(String id);
}
