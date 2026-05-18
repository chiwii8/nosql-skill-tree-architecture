package com.nosql_tree.skill.domain.dto;

import com.nosql_tree.skill.domain.model.Skill;

import java.util.List;
import java.util.Set;

/**
 * SkillRequest.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 17/05/2026
 */

public record SkillRequest(
     String name,
     String label,
     String description,
     String videoUrl,
     List<String> resources,
     Set<String> requirements
     ){}
