package com.nosql_tree.skill.infrastructure.outbound.neo4j;

import com.nosql_tree.skill.domain.dto.SkillTreeMap;
import com.nosql_tree.skill.domain.model.Skill;
import com.nosql_tree.skill.domain.ports.outbound.SkillNeoRepositoryPort;
import com.nosql_tree.skill.infrastructure.outbound.SkillMapper;
import com.nosql_tree.user.domain.ports.outbound.UserMongoRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * SkillNeoAdapter.java
 * <p>
 * Description: This class is the adapter used for the implementation for Neo4j database, that should do at least the followings operations
 * <ul>
 *     <li>Save</li>
 *     <li>Delete</li>
 *     <li>Add Dependencies between Nodes</li>
 *     <li>Search a Node by is Slug</li>
 *     <li>Know if can unlock the next node</li>
 *     <li>Know the requirement of unlocking the next node</li>
 * </ul>
 *
 * @author aleja
 * @since 13/05/2026
 */

@Component
public class SkillNeoAdapter implements SkillNeoRepositoryPort {

    private final SkillDataNeoRepository skillDataNeoRepository;
    private final UserMongoRepositoryPort userMongoRepositoryPort;

    public SkillNeoAdapter(SkillDataNeoRepository skillDataNeoRepository, UserMongoRepositoryPort userMongoRepositoryPort) {
        this.skillDataNeoRepository = skillDataNeoRepository;
        this.userMongoRepositoryPort = userMongoRepositoryPort;
    }

    @Override
    public void save(Skill skill) {
        skillDataNeoRepository.save(SkillMapper.toNode(skill));
    }

    @Override
    public void addDependency(String skillSlug, String prerequisiteSlug) {
        skillDataNeoRepository.addRequirements(skillSlug,prerequisiteSlug);
    }

    @Override
    public Optional<Skill> findBySlug(String slug) {
        return skillDataNeoRepository.findById(slug)
                .map(SkillMapper::toDomain);
    }

    @Override
    public List<Skill> findByLabel(String label) {


        return skillDataNeoRepository.findByLabel(label)
                .stream().map(SkillMapper::toDomain)
                .toList();
    }

    @Override
    public boolean canUnlock(String userEmail, String skillSlug) {
        List<String> requiredSkills = skillDataNeoRepository.getPrerequisitePath(skillSlug);

        if(requiredSkills.isEmpty())
            return true;
        return userMongoRepositoryPort.findByEmail(userEmail)
                .map(user -> user.getUnlockedSkills().containsAll(requiredSkills))
                .orElse(false);
    }

    @Override
    public List<String> getPrerequisitePath(String skillSlug) {
        return skillDataNeoRepository.getPrerequisitePath(skillSlug);
    }

    @Override
    public boolean existsBySlug(String slug) {
        return skillDataNeoRepository.existsById(slug);
    }

    @Override
    public void deleteBySlug(String slug) {
        skillDataNeoRepository.deleteById(slug);
    }

    @Override
    public boolean hasDependencies(String slug) {
        return skillDataNeoRepository.hasDependencies(slug);
    }

    @Override
    public List<Skill> findAllSkills() {
        return skillDataNeoRepository.findAll()
                .stream().map(SkillMapper::toDomain)
                .toList();
    }

    @Override
    public List<SkillTreeMap.SkillEdge> findAllDependencies() {
        List<Map<String, String>> edgesData = skillDataNeoRepository.findAllEdges();

        // 2. Mapeamos el resultado de la consulta Cypher al objeto de Dominio
        return edgesData.stream()
                .map(edge -> new SkillTreeMap.SkillEdge(
                        edge.get("fromSlug"),
                        edge.get("toSlug")
                ))
                .toList();
    }

    @Override
    public Skill updateSkillAndRelationships(String currentSlug, Skill updatedSkill, Set<String> prerequisiteSlugs) {
        skillDataNeoRepository.updateNodeProperties(currentSlug, updatedSkill.getSlug(), updatedSkill.getName(), updatedSkill.getLabel());

        // B. Borrar flechas de requisitos viejas
        skillDataNeoRepository.deleteAllPrerequisites(updatedSkill.getSlug());

        // C. Crear las flechas de requisitos nuevas
        if (prerequisiteSlugs != null) {
            for (String preSlug : prerequisiteSlugs) {
                skillDataNeoRepository.addRequirements(updatedSkill.getSlug(), preSlug);
            }
        }

        return updatedSkill;
    }
}
