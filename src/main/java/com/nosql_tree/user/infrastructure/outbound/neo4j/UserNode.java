package com.nosql_tree.user.infrastructure.outbound.neo4j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * UserNode.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 08/05/2026
 */

@Node("User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNode {
    @Id
    private String id;

    private String name;
    private int level;



}
