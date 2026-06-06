package com.nosql_tree.user.application.services.user.crud;

import com.nosql_tree.user.application.services.crud.UpdateUserService;
import com.nosql_tree.user.domain.exception.UserNotFoundException;
import com.nosql_tree.user.domain.model.User;
import com.nosql_tree.user.domain.ports.outbound.UserMongoRepositoryPort;
import com.nosql_tree.user.domain.ports.outbound.UserNeo4jRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * UpdateUserServiceTest.java
 * <p>
 * Description: unit test for the service of the UpdateService management with MongoDB Neo4j
 *
 * @author aleja
 * @since 26/05/2026
 */

class UpdateUserServiceTest {

    private UserMongoRepositoryPort userMongoRepositoryPort;
    private UserNeo4jRepositoryPort userNeo4jRepositoryPort;

    private PasswordEncoder passwordEncoder; // Si manejas cambio de contraseña

    private UpdateUserService updateUserService; // El servicio que vamos a probar

    @BeforeEach
    void setUp(){
        userMongoRepositoryPort = mock(UserMongoRepositoryPort.class);
        userNeo4jRepositoryPort = mock(UserNeo4jRepositoryPort.class);

        updateUserService = new UpdateUserService(userMongoRepositoryPort,userNeo4jRepositoryPort);

    }

    @Test
    @DisplayName("Should update sucessfully the user")
    void shouldUpdateUserSuccessfully() {
        // 1. Arrange (Preparar los datos simulados y definir el comportamiento de los mocks)
        // 1. Arrange (Preparar los datos simulados y definir el comportamiento de los mocks)
        String userEmail = "alejandro@test.com";

        User existingUser = new User();
        existingUser.setEmail(userEmail);
        existingUser.setName("Alejandro Original");
        existingUser.setPassword("encoded_old_password");

        User updatedData = new User();
        updatedData.setEmail(userEmail);
        updatedData.setName("Alejandro Actualizado");
        updatedData.setPassword("new_raw_password");

        // 2. Act (Ejecutar el método del servicio)
        when(userMongoRepositoryPort.findByEmail(userEmail)).thenReturn(Optional.of(existingUser));
        // Simulamos el cifrado de la nueva contraseña
        when(passwordEncoder.encode("new_raw_password")).thenReturn("encoded_new_password");
        // 3. Assert (Verificar que se guardó el cambio y los datos coinciden)

        User result = updateUserService.updateUser(updatedData);

        // 3. Assert (Verificar que se guardó el cambio y los datos coinciden)
        assertNotNull(result);
        assertEquals("Alejandro Actualizado", result.getName());
        assertEquals("encoded_new_password", result.getPassword());

        // Verificamos que se guardó de forma bimodal en ambas bases de datos
        verify(userMongoRepositoryPort, times(1)).save(any(User.class));
        verify(userNeo4jRepositoryPort, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Validar que el caso de uso reaccione correctamente ante errores de negocio
        // 1. Arrange
        String userEmail = "inexistente@test.com";
        User updatedData = new User();
        updatedData.setName("Cualquier Nombre");

        // Simulamos que no se encuentra al usuario en la base de datos
        when(userMongoRepositoryPort.findByEmail(userEmail)).thenReturn(Optional.empty());

        // 2. Act & 3. Assert
        assertThrows(UserNotFoundException.class, () -> updateUserService.updateUser(updatedData));

        // Verificamos que el flujo se detuvo y NUNCA se intentó guardar nada ni cifrar contraseñas
        verify(passwordEncoder, never()).encode(anyString());
        verify(userMongoRepositoryPort, never()).save(any(User.class));
        verify(userNeo4jRepositoryPort, never()).save(any(User.class));
    }
}
