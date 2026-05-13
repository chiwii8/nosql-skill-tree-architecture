package com.nosql_tree.user.infrastructure.outbound.neo4j;

import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.user.domain.ports.outbound.UserNeo4jRepositoryPort;
import com.nosql_tree.user.infrastructure.outbound.UserMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * UserGraphAdapter.java
 * <p>
 * Description: Implementation of the Adapter for the use of Neo4J database, in this database we should save the minimum data for the identification of the user,
 * in this case we use the ID generate to relation this Node with the in live Document in MongoDB for the model of User.
 *
 * @author aleja
 * @since 08/05/2026
 */

@Component
public class UserGraphAdapter implements UserNeo4jRepositoryPort {

    private final Neo4jUserRepository neo4jUserRepository;

    public UserGraphAdapter(Neo4jUserRepository neo4jUserRepository) {
        this.neo4jUserRepository = neo4jUserRepository;
    }

    @Override
    public void save(User user) {
        UserNode node = UserMapper.toNode(user);
        neo4jUserRepository.save(node);
    }

    @Override
    public void deleteById(String id) {
        neo4jUserRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(String id) {
        return neo4jUserRepository.findById(id)
                .map(UserMapper::toDomain);
    }


}
