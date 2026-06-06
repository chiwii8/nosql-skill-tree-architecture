package com.nosql_tree.skill.infrastructure.outbound.neo4j;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public interface SkillDataNeoRepository extends Neo4jRepository<SkillNode, String> {

    @Query("MATCH (a:Skill {slug: $targetSlug}), (b:Skill {slug: $preSlug}) " +
            "MERGE (a)-[:REQUIRES]->(b)")
    void addRequirements(String targetSlug, String preSlug);

    @Query("MATCH (s:Skill {slug: $skillSlug})-[:REQUIRES*1..]->(pre:Skill) " +
            "RETURN pre.slug")
    List<String> getPrerequisitePath(String skillSlug);

    @Query("MATCH (dependent:Skill)-[:REQUIRES]->(target:Skill {slug: $slug}) " +
            "RETURN count(dependent) > 0")
    boolean hasDependencies(@Param("slug") String slug);

    @Query("MATCH (dependent:Skill)-[:REQUIRES]->(required:Skill) " +
            "RETURN dependent.slug + '|' + required.slug")
    List<String> findAllEdges();

    @Query("MATCH (s:Skill {label: $label}) RETURN s")
    List<SkillNode> findByLabel(String label);

    @Query("MATCH (s:Skill {slug: $slug})-[r:REQUIRES]->() DELETE r")
    void deleteAllPrerequisites(String slug);

    // 2. Modifica las propiedades básicas del nodo (y actualiza el slug si cambió)
    @Query("MATCH (s:Skill {slug: $currentSlug}) " +
            "SET s.slug = $newSlug, s.name = $name, s.label = $label")
    void updateNodeProperties(String currentSlug, String newSlug, String name, String label);

}
