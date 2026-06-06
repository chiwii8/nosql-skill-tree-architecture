package com.nosql_tree.user.domain.model.dto;

import com.nosql_tree.user.domain.model.Role;

import java.util.Set;

public record AuthResponse(
        String token,
        String email,
        String name,
        Role role,
        Set<String> completedSkills
) { }
