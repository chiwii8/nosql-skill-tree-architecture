package com.nosql_tree.skill.domain.exception;

/**
 * SkillNotFoundException.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 14/05/2026
 */

public class SkillNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "The Skill cannot be found in the database";

    public SkillNotFoundException(){
        super(DEFAULT_MESSAGE);
    }

    public SkillNotFoundException(String message){
        super(message);
    }
}
