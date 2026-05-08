package com.nosql_tree.user.infrastructure.outbound.mongodb;

import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.user.domain.ports.outbound.UserRepositoryPort;
import org.springframework.stereotype.Component;
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
    public User save(User user) {
        UserDocument document = UserMapper.toDocument(user);
        mongoRepository.save(document);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return  mongoRepository.findById(id)
                .map(UserMapper::toDomain);
    }

    @Override
    public void deleteById(String id){
        mongoRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id){
        return mongoRepository.existsById(id);
    }

    @Override
    public User updateUser(User user){
        UserDocument userDocument = UserMapper.toDocument(user);
        UserDocument updatedUser = mongoRepository.save(userDocument);

        return UserMapper.toDomain(updatedUser);
    }

    @Override
    public Optional<User> findByEmail(String email){
        return mongoRepository.findByEmail(email)
                .map(UserMapper::toDomain);

    }

    @Override
    public boolean existsByEmail(String email) {
        return mongoRepository.existsByEmail(email);
    }
}
