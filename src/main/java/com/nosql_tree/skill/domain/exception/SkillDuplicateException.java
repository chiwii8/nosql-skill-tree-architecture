package com.nosql_tree.skill.domain.exception;

/**
 * SkillDuplicateException.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 17/05/2026
 */

public class SkillDuplicateException extends RuntimeException{
    public SkillDuplicateException(String message){
        super(message);
    }
}
