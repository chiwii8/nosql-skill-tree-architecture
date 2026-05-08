package com.nosql_tree.user.domain.ports.outbound;

import com.nosql_tree.user.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(String id);
    void deleteById(String id);
    boolean existsById(String id);
    User updateUser(User user);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
