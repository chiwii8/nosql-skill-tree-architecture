package com.nosql_tree.skill.infrastructure.outbound.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.neo4j.core.schema.Id;

import java.util.List;

/**
 * SkillDocument.java
 * <p>
 * Description: implementation of the Skill class for mongodb adapter using the default mongodb mapping for document
 *
 * @author aleja
 * @since 13/05/2026
 */

@Document(collection = "skills")
@Data
public class SkillDocument {
    @Id
    private String slug;
    private String name;
    private String label;
    private String description;
    private String videoURL;
    private List<String> resources;

    private String generateSlug(String name, String label){
        return (label + "-" + name).toLowerCase()
                .replace(" ","-")
                .replace("[^a-z0-9-]","");
    }

    public SkillDocument(String name, String label, String description, String videoURL, List<String> resources) {
        this.slug = generateSlug(name,label);
        this.name = name;
        this.label = label;
        this.description = description;
        this.videoURL = videoURL;
        this.resources = resources;
    }
}
