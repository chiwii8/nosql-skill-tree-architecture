package com.nosql_tree.user.domain.ports.outbound;

import com.nosql_tree.user.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    void save(User user);
    Optional<User> findById(String id);
}
