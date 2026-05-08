package com.nosql_tree.user.domain.ports.inbound;

public interface DeleteUserPort {
    void deleteUserById(String id);
}
