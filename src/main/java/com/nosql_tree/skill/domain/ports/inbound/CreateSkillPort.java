package com.nosql_tree.skill.domain.ports.inbound;

import com.nosql_tree.skill.domain.model.Skill;

import java.util.Set;

public interface CreateSkillPort {
    void createSkill(Skill skill, Set<String> prerequisites);
}
