package com.nosql_tree.skill.infrastructure.outbound.neo4j;

import com.nosql_tree.skill.domain.model.Skill;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

/**
 * UserDocument.java
 * <p>
 * Description: Adapter of the {@code Skill} java class for the use in Neo4J
 *
 * @author aleja
 * @since 06/05/2026
 */

@Data
@NoArgsConstructor
@Node("Skill")
public class SkillNode {


    @Id
    private String slug;

    private String label;

    private String name;

    /// Relation, indicate the requirements needed to unlock this skill
    @Relationship(type = "REQUIRES", direction = Relationship.Direction.OUTGOING)
    private Set<SkillNode> requirements;

    public SkillNode(String label, String name) {
        this.slug = generateSlug(label,name);
        this.label = label;
        this.name = name;

    }

    public SkillNode(String label, String name, Set<SkillNode> requirements) {
        this.slug = generateSlug(label,name);
        this.label = label;
        this.name = name;
        this.requirements = requirements;
    }

    private String generateSlug(String label, String name){
        return (label + "-" + name).toLowerCase()
                .replace(" ","-")
                .replace("[^a-z0-9-]","");
    }
}
