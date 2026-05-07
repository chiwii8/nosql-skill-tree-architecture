package com.nosql_tree.user.infrastructure.outbound.mongodb;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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

    private String id;
    private String name;
    private List<String> unlockedSkills;
}
