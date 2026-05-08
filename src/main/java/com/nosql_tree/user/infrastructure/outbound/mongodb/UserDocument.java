package com.nosql_tree.user.infrastructure.outbound.mongodb;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * UserDocument.java
 * <p>
 * Description: Adapter for the model class user.java for the implement a document for MongoDB implementation
 *
 * @author aleja
 * @since 06/05/2026
 */

@Document(collection = "users")
@Data
@NoArgsConstructor
public class UserDocument {
    @Id
    private String id;
    private String name;
    private List<String> unlockedSkills;

    ///UserAccount variables, use in the same clase for short time and more control of the document
    @Indexed(unique = true)
    private String email;   // Represents the username in the login
    private String password; // Should be hashed

    private String role;


    /// More info for MongoDB document
    private Map<String, Object> metadata;
    private String lastLogin;

    private int level;  //Actual level of advanced in the tree
}
