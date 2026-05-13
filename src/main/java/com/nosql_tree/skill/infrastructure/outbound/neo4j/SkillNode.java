package com.nosql_tree.skill.infrastructure.outbound;

import com.nosql_tree.skill.domain.model.Skill;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class SkillNode {

    private String id;

    private String name;

    private String description;

    private int status;


    /// Relation, indicate the requirements needed to unlock this skill
    private Set<Skill> requirements;
}
