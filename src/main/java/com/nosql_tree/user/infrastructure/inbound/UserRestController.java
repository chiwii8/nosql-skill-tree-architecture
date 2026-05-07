package com.nosql_tree.user.infrastructure.inbound;

import com.nosql_tree.user.domain.exception.UserNotFoundException;
import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.user.domain.ports.inbound.GetUserPort;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserRestController.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 06/05/2026
 */

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final GetUserPort getUserPort;

    public UserRestController(GetUserPort getUserPort) {
        this.getUserPort = getUserPort;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        if(id == null || id.isBlank())
            return ResponseEntity.badRequest().build();

        try{
            User user = getUserPort.getUserById(id);
            return ResponseEntity.ok(user);

        }catch(UserNotFoundException  e){
            return ResponseEntity.notFound().build();
        }
    }
}
