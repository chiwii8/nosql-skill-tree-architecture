package com.nosql_tree.user.domain.ports.outbound;

import com.nosql_tree.user.domain.model.User;

import java.util.Optional;

public interface UserNeo4jRepositoryPort {
    void save(User user);

    void deleteById(String id);

    Optional<User> findById(String id);

}
