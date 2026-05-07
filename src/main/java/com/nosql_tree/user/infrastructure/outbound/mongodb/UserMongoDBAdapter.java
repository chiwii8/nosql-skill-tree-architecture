package com.nosql_tree.user.infrastructure.outbound.mongodb;

import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.user.domain.ports.outbound.UserRepositoryPort;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * UserMongoDBAdapter.java
 * <p>
 * Description: implementation of the adapter of the mongoDB using the default interface of UserRepositoryPort
 *
 * @author aleja
 * @since 06/05/2026
 */

@Component
public class UserMongoDBAdapter implements UserRepositoryPort {

    private final SpringDataMongoRepository mongoRepository;

    public UserMongoDBAdapter(SpringDataMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public void save(User user) {
        UserDocument document = UserMapper.toDocument(user);
        mongoRepository.save(document);
    }

    @Override
    public Optional<User> findById(String id) {
        return  mongoRepository.findById(id)
                .map(UserMapper::toDomain);
    }
}
