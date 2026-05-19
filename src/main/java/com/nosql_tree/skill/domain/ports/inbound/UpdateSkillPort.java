package com.nosql_tree.skill.domain.ports.inbound;


import com.nosql_tree.skill.domain.model.Skill;


import java.util.Set;

public interface UpdateSkillPort {
    Skill update(String currentSlug, Skill updatedSkill, Set<String> newPrerequisiteSlugs);
}
