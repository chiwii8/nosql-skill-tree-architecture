package com.nosql_tree.user.infrastructure.outbound.mongodb;

import com.nosql_tree.user.domain.model.User;

/**
 * userMapper.java
 * <p>
 * Description: Implementation of the translation the model class user.java to the different Adapter endpoints.
 *
 *
 * @author aleja
 * @since 06/05/2026
 */

public class UserMapper {

    private UserMapper(){}

    /**
     * translate the model class to Document of MongoDB
     * @param user class to translate
     * @return user document class implementation translated
     */
    public static UserDocument toDocument(User user){
        UserDocument document = new UserDocument();

        document.setId(user.getId());
        document.setName(user.getName());
        document.setUnlockedSkills(user.getUnlockedSkills());
        document.setEmail(user.getEmail());
        document.setPassword(user.getPassword());
        document.setRole(user.getRole());
        document.setMetadata(user.getMetadata());
        document.setLastLogin(user.getLastLogin());
        document.setLevel(user.getLevel());

        return document;
    }

    /**
     * translate the Document user to domain implementation
     * @param document type of translation
     * @return user domain class translated from
     */
    public static User toDomain(UserDocument document){
        // Avoid the Exception NullPointerException
        if(document == null){
            return null;
        }

        return new User(
                document.getId(),
                document.getName(),
                document.getUnlockedSkills(),
                document.getEmail(),
                document.getPassword(),
                document.getRole(),
                document.getMetadata(),
                document.getLastLogin(),
                document.getLevel()
        );
    }
}
