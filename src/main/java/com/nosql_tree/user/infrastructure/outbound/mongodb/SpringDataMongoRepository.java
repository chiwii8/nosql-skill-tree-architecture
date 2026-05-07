package com.nosql_tree.user.infrastructure.outbound.mongodb;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMongoRepository extends MongoRepository<UserDocument, String> {
}
