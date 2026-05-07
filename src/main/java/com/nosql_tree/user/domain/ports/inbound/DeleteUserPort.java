package com.nosql_tree.user.domain.ports.inbound;

public interface DeleteUserPort {
    boolean deleteUserById(String id);
}
