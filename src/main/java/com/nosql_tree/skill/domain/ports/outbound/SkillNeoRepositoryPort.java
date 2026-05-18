package com.nosql_tree.skill.domain.ports.outbound;

import com.nosql_tree.skill.domain.dto.SkillTreeMap;
import com.nosql_tree.skill.domain.model.Skill;

import java.util.List;
import java.util.Optional;

public interface SkillRepositoryPort {
    void save(Skill skill);
    void addDependency(String skillSlug, String prerequisiteSlug);
    Optional<Skill> findBySlug(String slug);
    List<Skill> findByLabel(String label);
    boolean canUnlock(String userEmail, String skillSlug);
    List<String> getPrerequisitePath(String skillSlug);
    boolean existsBySlug(String slug);
    void deleteBySlug(String slug);

    boolean hasDependencies(String slug);

    List<Skill> findAllSkills();
    List<SkillTreeMap.SkillEdge> findAllDependencies();
    Skill updateSkillAndRelationships(String currentSlug, Skill updatedSkill, List<String> prerequisiteSlugs);
}
