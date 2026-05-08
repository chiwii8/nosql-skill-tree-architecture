package com.nosql_tree.user.util;

import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.util.HashPasswordUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

/**
 * HashPaswordUtilTest.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 07/05/2026
 */

public class HashPaswordUtilTest {



    @Test
    @DisplayName("Should encrypt password and verify it correctly")
    void shouldEncryptAndVerify() {
        String password = "mySecretPassword123";

        // Act
        String hashedPassword = HashPasswordUtil.encryptPassword(password);

        // Assert
        assertNotNull(hashedPassword);
        assertNotEquals(password, hashedPassword); // El hash no debe ser igual al texto plano
        assertTrue(HashPasswordUtil.verify(password, hashedPassword)); // Debe verificar bien
    }

    @Test
    @DisplayName("Should return false when password does not match")
    void shouldFailOnWrongPassword() {
        String password = "correctPassword";
        String wrongPassword = "wrongPassword";

        String hashedPassword = HashPasswordUtil.encryptPassword(password);

        assertFalse(HashPasswordUtil.verify(wrongPassword, hashedPassword));
    }
}

