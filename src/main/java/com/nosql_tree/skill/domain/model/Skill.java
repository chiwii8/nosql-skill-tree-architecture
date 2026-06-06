package com.nosql_tree.skill.domain.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    /// Should be unique: label-name
    private String slug;
    /// This variable define the definition of all the tree
    /// Example
    private String label;

    private String name;

    private String description;

    private String videoUrl;

    private List<String> resources;

    /// Relation, indicate the requirements needed to unlock this skill
    /// Only used in Neo4j
    private Set<String> requirements;

    public Skill(String label, String name, Set<String> requirements) {
        this.slug = generateSlug(label,name);
        this.label = label;
        this.name = name;
        this.requirements = requirements != null ? requirements : new HashSet<>();
    }

    public Skill(String label, String name, String description, String videoUrl, List<String> resources) {
        this.slug = generateSlug(label, name);
        this.label = label;
        this.name = name;
        this.description = description;
        this.videoUrl = videoUrl;
        this.resources = resources != null ? resources : new ArrayList<>();
    }

    public Skill(String label, String name, String description, String videoUrl, List<String> resources, Set<String> requirements) {
        this.slug = generateSlug(label, name);
        this.label = label;
        this.name = name;
        this.description = description;
        this.videoUrl = videoUrl;
        this.resources = resources != null ? resources : new ArrayList<>();
        this.requirements = requirements != null ? requirements : new HashSet<>();
    }

    public Skill(Skill skill){
        this.slug = generateSlug(skill.label,skill.name);
        this.label = skill.label;
        this.name = skill.name;
        this.description = skill.description;
        this.videoUrl = skill.videoUrl;
        this.resources = skill.resources != null ? skill.resources : new ArrayList<>();
        this.requirements = skill.requirements != null ? skill.requirements : new HashSet<>();
    }

    ///Setters y Getters

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public Set<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(Set<String> requirements) {
        this.requirements = requirements;
    }

    private String generateSlug(String label, String name){
        return (label + "-" + name).toLowerCase()
                .replace(" ","-")
                .replace("[^a-z0-9-]","");
    }
}
