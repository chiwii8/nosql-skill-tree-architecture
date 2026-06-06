package com.nosql_tree.user.infrastructure.outbound;

import com.nosql_tree.user.domain.model.Role;
import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.user.domain.ports.outbound.UserMongoRepositoryPort;
import com.nosql_tree.user.domain.ports.outbound.UserNeo4jRepositoryPort;
import com.nosql_tree.util.HashPasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * UserSeeder.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 03/06/2026
 */

@Slf4j
@Profile("dev")
@Component
public class UserSeeder implements CommandLineRunner {
    private final UserMongoRepositoryPort userMongoRepositoryPort;
    private final UserNeo4jRepositoryPort userNeo4jRepositoryPort;

    public UserSeeder(UserMongoRepositoryPort userMongoRepositoryPort, UserNeo4jRepositoryPort userNeo4jRepositoryPort) {
        this.userMongoRepositoryPort = userMongoRepositoryPort;
        this.userNeo4jRepositoryPort = userNeo4jRepositoryPort;
    }

    @NullMarked
    @Override
    public void run(String... args) throws Exception {
        log.info("Starting the seeding process of skills from JSON ...");
        final String password = HashPasswordUtil.encryptPassword("securePassword1234");
        try{
            if(userMongoRepositoryPort.countUser()>0){
                log.info("The Database actually is seeded, finish the seeding process");
                return;
            }

            User admin = new User("Pedro","pedrito@admin.com", password, Role.ADMIN);
            User editor = new User("Juan","Juanito@editor.com",password,Role.EDITOR);
            User currentUser = new User("TestUser", "test@usuario.com",password,Role.USER);

            userMongoRepositoryPort.save(admin);
            userNeo4jRepositoryPort.save(admin);

            userMongoRepositoryPort.save(editor);
            userNeo4jRepositoryPort.save(editor);

            userMongoRepositoryPort.save(currentUser);
            userNeo4jRepositoryPort.save(currentUser);

            log.info("The User Seeder finish successfully");
        }catch (Exception ex){
            log.warn("The User Seeder Failed for unexpected error: {}",ex.getMessage());
        }
    }
}
