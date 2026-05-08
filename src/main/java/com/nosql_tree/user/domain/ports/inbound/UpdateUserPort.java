package com.nosql_tree.user.domain.ports.inbound;

import com.nosql_tree.user.domain.model.User;

public interface UpdateUserPort {
    User updateUser(User user);
}
