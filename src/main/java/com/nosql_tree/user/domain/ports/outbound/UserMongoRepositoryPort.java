package com.nosql_tree.user.domain.ports.outbound;

import com.nosql_tree.user.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserMongoRepositoryPort {
    User save(User user);
    Optional<User> findById(String id);
    void deleteById(String id);
    boolean existsById(String id);
    User updateUser(User user);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Set<String> getCompletedSkills(String email);
    void addCompletedSkill(String email, String slug);
    long  countUser();
}
