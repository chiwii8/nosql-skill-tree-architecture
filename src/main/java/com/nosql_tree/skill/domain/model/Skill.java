package com.nosql_tree.skill.domain.model;

import java.util.Set;

/**
 * skill.java
 * <p>
 * Description: represent the skill or know acquired in the learning travel.
 * Model the basic properties and manage the tree requirements unlock dependencies.
 * Base Model following the Hexagonal Architecture
 *
 * @author aleja
 * @since 06/05/2026
 */

public class Skill {
    private String id;

    private String name;

    private String description;

    private int status;


    /// Relation, indicate the requirements needed to unlock this skill
    private Set<Skill> requirements;
}
