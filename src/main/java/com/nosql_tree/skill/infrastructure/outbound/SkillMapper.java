package com.nosql_tree.skill.infrastructure.outbound;

import com.nosql_tree.skill.domain.dto.SkillRequest;
import com.nosql_tree.skill.domain.model.Skill;
import com.nosql_tree.skill.infrastructure.outbound.mongodb.SkillDocument;
import com.nosql_tree.skill.infrastructure.outbound.neo4j.SkillNode;

import java.util.HashSet;
import java.util.Set;

/**
 * SkillMapper.java
 * <p>
 * Description: Implementation of the translation the model class skill.java to the different Adapter endpoints
 *
 * @author aleja
 * @since 13/05/2026
 */

public class SkillMapper {
    private SkillMapper(){}

    /**
     * translate the model class to Node of Neo4j
     * In this method is not mapped the relations of requirements so, we delegated that to the cypher of the repository
     * @param skill class to translate
     * @return skill node class translated
     */
    public static SkillNode toNode(Skill skill){

        return new SkillNode(
                skill.getLabel(),
                skill.getName()
        );
    }

    /**
     * translate the model class to Document of MongoDB
     * @param skill class to translate
     * @return skill document translated
     */
    public static SkillDocument toDocument(Skill skill){
        return new SkillDocument(
                skill.getName(),
                skill.getLabel(),
                skill.getDescription(),
                skill.getVideoUrl(),
                skill.getResources()
        );
    }

    /**
     * translate the node skill class to model
     * @param skillNode class to translate
     * @return skill translated to model class
     */
    public static Skill toDomain(SkillNode skillNode){
        Set<String> prerequisiteSlugs = new HashSet<>();
        if (skillNode.getRequirements() != null) {
            skillNode.getRequirements().stream()
                    .map(SkillNode::getSlug) // Extrae el string del slug de cada nodo vecino
                    .forEach(prerequisiteSlugs::add);
        }
        return new Skill(
                skillNode.getLabel(),
                skillNode.getName(),
                prerequisiteSlugs
        );
    }

    /**
     * translate the node document class to model
     * @param skillDocument class to translate
     * @return skill translated to model class
     */
    public static Skill toDomain(SkillDocument skillDocument){
        return new Skill(
                skillDocument.getLabel(),
                skillDocument.getName(),
                skillDocument.getDescription(),
                skillDocument.getVideoURL(),
                skillDocument.getResources()
        );
    }

    /**
     * Take the translation of node and document and combine the result to obtain and model
     * object complete
     * @param mappedSkillDocument mapped document to model class
     * @param mappedSkillNode mapped node to model class
     * @return model class with all the data available
     */
    public static Skill toDomain(Skill mappedSkillDocument,Skill mappedSkillNode){
        return new Skill(
                mappedSkillDocument.getLabel(),
                mappedSkillDocument.getName(),
                mappedSkillDocument.getDescription(),
                mappedSkillDocument.getVideoUrl(),
                mappedSkillDocument.getResources(),
                mappedSkillNode.getRequirements()
        );
    }

    /**
     * translate the DTO request to model class
     * @param request dto to translate
     * @return skill model class translated
     */
    public static Skill toDomain(SkillRequest request){
        return new Skill(request.label(),request.name(),request.description(),request.videoUrl(),request.resources());
    }
}
