package com.nosql_tree.skill.domain.dto;

import com.nosql_tree.skill.domain.model.Skill;

import java.util.List;

/**
 * SkillTreeMap.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 17/05/2026
 */

public class SkillTreeMap {
    private List<Skill> nodes;
    private List<SkillEdge> edges;


    public SkillTreeMap(List<Skill> nodes, List<SkillEdge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public List<Skill> getNodes() {
        return nodes;
    }

    public List<SkillEdge> getEdges() {
        return edges;
    }

    public static class SkillEdge{
        private String fromSlug;
        private String toSlug;

        public SkillEdge(String fromSlug, String toSlug) {
            this.fromSlug = fromSlug;
            this.toSlug = toSlug;
        }

        public String getFromSlug() {
            return fromSlug;
        }

        public String getToSlug() {
            return toSlug;
        }
    }
}
