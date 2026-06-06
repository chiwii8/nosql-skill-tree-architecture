package com.nosql_tree.skill.application.services.crud;

import com.nosql_tree.skill.domain.dto.SkillTreeMap;
import com.nosql_tree.skill.domain.exception.SkillNotFoundException;
import com.nosql_tree.skill.domain.model.Skill;
import com.nosql_tree.skill.domain.ports.inbound.ReadSkillPort;
import com.nosql_tree.skill.domain.ports.outbound.SkillMongoRepositoryPort;
import com.nosql_tree.skill.domain.ports.outbound.SkillNeoRepositoryPort;
import com.nosql_tree.skill.infrastructure.outbound.SkillMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ReadSkillService.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 17/05/2026
 */

@Slf4j
@Service
public class ReadSkillService implements ReadSkillPort {

    private final SkillNeoRepositoryPort skillNeoRepositoryPort;
    private final SkillMongoRepositoryPort skillMongoRepositoryPort;

    public ReadSkillService(SkillNeoRepositoryPort skillNeoRepositoryPort, SkillMongoRepositoryPort skillMongoRepositoryPort) {
        this.skillNeoRepositoryPort = skillNeoRepositoryPort;
        this.skillMongoRepositoryPort = skillMongoRepositoryPort;
    }

    public SkillTreeMap getMap(){
        log.info("Loading all the Skill-map");

        List<Skill> nodes = skillNeoRepositoryPort.findAllSkills();

        List<SkillTreeMap.SkillEdge> edges = skillNeoRepositoryPort.findAllDependencies();

        log.info("Map Successfully charged");

        return new SkillTreeMap(nodes,edges);
    }


    @Override
    public Skill findBySlug(String slug) {
        if(slug == null || slug.isBlank()){
            log.error("The slug cannot be null or blank");
            throw new IllegalArgumentException("The slug cannot be null or blank");
        }

        log.debug("Executing findBySlug for: {}", slug);
        Optional<Skill> skillDocument = skillMongoRepositoryPort.findBySlug(slug);
        Optional<Skill> skillNode = skillNeoRepositoryPort.findBySlug(slug);

        if(skillDocument.isEmpty() || skillNode.isEmpty()){
            log.error("The skill {} cannot be found",slug);
            throw new SkillNotFoundException();
        }

        ///Mix the returned values into unique Skill
        return SkillMapper.toDomain(skillDocument.get(),skillNode.get());
    }

    /**
     * Actually not Realise
     * @param label
     * @return
     */
    @Override
    public List<Skill> findByLabel(String label) {
        if(label == null || label.isBlank()){
            log.error("The label cannot be null or blank");
            throw new IllegalArgumentException("The label cannot be null or blank");
        }

        throw new NotImplementedException();
    }

    @Override
    public SkillTreeMap getFullTree() {
        log.info("Generating full skill tree map from Neo4j...");

        // 1. Pedimos los nodos al puerto del adaptador
        List<Skill> documentSkill = skillMongoRepositoryPort.findAllSkills();

        if (documentSkill.isEmpty()) {
            log.info("The three is actually empty");
            return new SkillTreeMap(Collections.emptyList(),Collections.emptyList());
        }

        List<Skill> nodes = skillNeoRepositoryPort.findAllSkills();

        // 2. Pedimos las conexiones (aristas) al puerto del adaptador
        List<SkillTreeMap.SkillEdge> edges = skillNeoRepositoryPort.findAllDependencies();

        Map<String, Set<String>> graphRequirementsMap = nodes.stream()
                .collect(Collectors.toMap(Skill::getSlug, Skill::getRequirements, (a, b) -> a));

        for(Skill skill: documentSkill){
            Set<String> requirements = graphRequirementsMap.getOrDefault(skill.getSlug(), Collections.emptySet());
            skill.setRequirements(requirements);
        }
        log.info("Tree map generated successfully. Nodes: {}, Edges: {}", nodes.size(), edges.size());
        return new SkillTreeMap(documentSkill, edges);
    }
}
