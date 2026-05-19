package com.nosql_tree.skill.domain.exception;

/**
 * SkillAlreadyExistsException.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 14/05/2026
 */

public class SkillAlreadyExistsException extends RuntimeException{
    public static final String DEFAULT_MESSAGE = "The Skill already exists in the Database";

    public SkillAlreadyExistsException(){
        super(DEFAULT_MESSAGE);
    }
}
