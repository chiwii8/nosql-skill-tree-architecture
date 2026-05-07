package com.nosql_tree.user.domain.ports.inbound;


import com.nosql_tree.user.domain.model.User;

public interface CreateUserPort {
    User createUser(User user);
}
