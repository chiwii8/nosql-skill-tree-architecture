package com.nosql_tree.skill.domain.ports.inbound;

import com.nosql_tree.skill.domain.dto.SkillTreeMap;
import com.nosql_tree.skill.domain.model.Skill;

import java.util.List;

public interface ReadSkillPort {
    Skill findBySlug(String slug);
    List<Skill> findByLabel(String label);
    SkillTreeMap getFullTree();
}
