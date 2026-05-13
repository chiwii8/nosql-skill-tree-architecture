package com.nosql_tree.user.domain.model.dto;

public record AuthResponse(
        String token,
        String email,
        String name,
        String role
) { }
