package com.nosql_tree.skill.infrastructure.outbound.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SkillDataMongoRepository extends MongoRepository<SkillDocument,String> {
}
