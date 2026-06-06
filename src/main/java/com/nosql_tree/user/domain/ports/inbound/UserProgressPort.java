package com.nosql_tree.user.domain.ports.inbound;


import java.util.Set;

public interface UserProgressPort  {
    void addCompleteSkill(String email, String skillSlug);
    Set<String> getCompletedSkills(String email);
}
