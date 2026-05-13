package com.nosql_tree.user.infrastructure.outbound.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface Neo4jUserRepository extends Neo4jRepository<UserNode,String> {
}
