package com.nosql_tree.user.infrastructure.inbound;

import com.nosql_tree.user.domain.exception.UserNotFoundException;
import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.user.domain.ports.inbound.CreateUserPort;
import com.nosql_tree.user.domain.ports.inbound.DeleteUserPort;
import com.nosql_tree.user.domain.ports.inbound.GetUserPort;
import com.nosql_tree.user.domain.ports.inbound.UpdateUserPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * UserRestController.java
 * <p>
 * Description: Controller that wrapper the CRUD operations for the User class
 *
 * @author aleja
 * @since 06/05/2026
 */

// TODO: We dont control the modifications over the different variables in the corresponding service, so could desencatenate in the followings error:
// UpdateUser: When we update in this section we can overtake the email from already existing it, change the password for a plane password
// The change of the email can cause problems when we implements the login for cant correctly identified the account searched.

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final GetUserPort getUserPort;
    private final CreateUserPort createUserPort;
    private final DeleteUserPort deleteUserPort;
    private final UpdateUserPort updateUserPort;

    public UserRestController(GetUserPort getUserPort, CreateUserPort createUserPort, DeleteUserPort deleteUserPort, UpdateUserPort updateUserPort) {
        this.getUserPort = getUserPort;
        this.createUserPort = createUserPort;
        this.deleteUserPort = deleteUserPort;
        this.updateUserPort = updateUserPort;
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


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        if(user == null){
            return ResponseEntity.badRequest().build();
        }

        try{
            User newuser = createUserPort.createUser(user);
            return new ResponseEntity<>(newuser, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        if(id == null || id.isBlank())
            return ResponseEntity.badRequest().build();

        try{
            deleteUserPort.deleteUserById(id);
            return ResponseEntity.ok().build();
        }catch (UserNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        String id = user.getId();
        if(id == null || id.isBlank())
            return ResponseEntity.badRequest().build();

        try{
            User updatedUser = updateUserPort.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        }catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
