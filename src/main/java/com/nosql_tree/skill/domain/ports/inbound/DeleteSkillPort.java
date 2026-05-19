package com.nosql_tree.skill.domain.ports.inbound;

public interface DeleteSkillPort {
    void deleteBySlug(String slug);
}
