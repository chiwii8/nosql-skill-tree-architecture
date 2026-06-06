package com.nosql_tree.user.infrastructure.outbound.mongodb;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataMongoRepository extends MongoRepository<UserDocument, String> {
    Optional<UserDocument> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("{ 'email' : ?0 }")
    @Update("{ '$addToSet' : { 'completedSkills' : ?1 } }")
    void addCompletedSkill(String email, String skillSlug);
}
