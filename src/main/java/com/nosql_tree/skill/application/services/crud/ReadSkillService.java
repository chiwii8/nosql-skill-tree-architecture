package com.nosql_tree.skill.application.services.crud;

import com.nosql_tree.skill.domain.dto.SkillTreeMap;
import com.nosql_tree.skill.domain.exception.SkillNotFoundException;
import com.nosql_tree.skill.domain.model.Skill;
import com.nosql_tree.skill.domain.ports.inbound.ReadSkillPort;
import com.nosql_tree.skill.domain.ports.outbound.SkillNeoRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public ReadSkillService(SkillNeoRepositoryPort skillNeoRepositoryPort) {
        this.skillNeoRepositoryPort = skillNeoRepositoryPort;
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
        Optional<Skill> skill = skillNeoRepositoryPort.findBySlug(slug);

        if(skill.isEmpty()){
            log.error("The skill {} cannot be found",slug);
            throw new SkillNotFoundException();
        }

        return skill.get();
    }

    @Override
    public List<Skill> findByLabel(String label) {
        if(label == null || label.isBlank()){
            log.error("The label cannot be null or blank");
            throw new IllegalArgumentException("The label cannot be null or blank");
        }

        return null;
    }

    @Override
    public SkillTreeMap getFullTree() {
        log.info("Generating full skill tree map from Neo4j...");

        // 1. Pedimos los nodos al puerto del adaptador
        List<Skill> nodes = skillNeoRepositoryPort.findAllSkills();

        // 2. Pedimos las conexiones (aristas) al puerto del adaptador
        List<SkillTreeMap.SkillEdge> edges = skillNeoRepositoryPort.findAllDependencies();

        log.info("Tree map generated successfully. Nodes: {}, Edges: {}", nodes.size(), edges.size());
        return new SkillTreeMap(nodes, edges);
    }
}
