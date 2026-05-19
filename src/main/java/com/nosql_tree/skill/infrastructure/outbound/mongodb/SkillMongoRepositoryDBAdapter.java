package com.nosql_tree.skill.infrastructure.outbound.mongodb;

import com.nosql_tree.skill.domain.model.Skill;
import com.nosql_tree.skill.domain.ports.outbound.SkillMongoRepositoryPort;
import com.nosql_tree.skill.infrastructure.outbound.SkillMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * SkillMongoRepositoryDBAdapter.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 13/05/2026
 */

@Component
public class SkillMongoRepositoryDBAdapter implements SkillMongoRepositoryPort {

    private final SkillDataMongoRepository mongoRepository;

    public SkillMongoRepositoryDBAdapter(SkillDataMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public void save(Skill skill) {
        SkillDocument document = SkillMapper.toDocument(skill);
        mongoRepository.save(document);
    }

    @Override
    public Optional<Skill> findBySlug(String slug) {
        return mongoRepository.findById(slug)
                .map(SkillMapper::toDomain);
    }

    @Override
    public List<Skill> findAllSkills() {
        return mongoRepository.findAll()
                .stream().map(SkillMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteBySlug(String slug) {
        mongoRepository.deleteById(slug);
    }
}
