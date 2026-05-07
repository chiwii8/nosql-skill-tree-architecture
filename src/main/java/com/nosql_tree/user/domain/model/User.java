package com.nosql_tree.user.domain.model;

import java.util.List;

/**
 * User.java
 * <p>
 * Description: Implementation of the basic user model saving the required id knows
 *
 * @author aleja
 * @since 06/05/2026
 */

public class User {
    private String id;
    private String name;
    private List<String> unlockedSkills;

    public User(String id, String name, List<String> unlockedSkills) {
        this.id = id;
        this.name = name;
        this.unlockedSkills = unlockedSkills;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUnlockedSkills() {
        return unlockedSkills;
    }

    public void setUnlockedSkills(List<String> unlockedSkills) {
        this.unlockedSkills = unlockedSkills;
    }
}
