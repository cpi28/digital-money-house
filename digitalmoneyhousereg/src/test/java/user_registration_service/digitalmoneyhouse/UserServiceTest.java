package user_registration_service.digitalmoneyhouse;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import user_registration_service.digitalmoneyhouse.userregistration.dto.UserRegistrationRequest;
import user_registration_service.digitalmoneyhouse.userregistration.dto.UserRegistrationResponse;
import user_registration_service.digitalmoneyhouse.userregistration.exception.UserAlreadyExistsException;
import user_registration_service.digitalmoneyhouse.userregistration.model.User;
import user_registration_service.digitalmoneyhouse.userregistration.repository.UserRepository;
import user_registration_service.digitalmoneyhouse.userregistration.service.UserService;
import user_registration_service.digitalmoneyhouse.userregistration.service.impl.UserServiceImpl;
import user_registration_service.digitalmoneyhouse.userregistration.util.AliasGenerator;
import user_registration_service.digitalmoneyhouse.userregistration.util.CVUGenerator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRegistrationRequest validRequest;

    @Mock
    private CVUGenerator cvuGenerator;

    @Mock
    private AliasGenerator aliasGenerator;

    @BeforeEach
    void setUp() {
        validRequest = new UserRegistrationRequest(
                "Juan", "Pérez", "12345678",
                "juan.perez@example.com", "+541112345678",
                "password123"
        );
        lenient().when(cvuGenerator.generateCVU()).thenReturn("1234567890123456789012");
        lenient().when(aliasGenerator.generateAlias()).thenReturn("mi-alias-digital");
        lenient().when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L); // Simular que el usuario se guarda con ID 1
            return user;
        });
    }

    @Test
    void registerUser_ShouldRegisterSuccessfully_WhenUserDoesNotExist() {
        // Simular que el usuario no existe en la BD
        lenient().when(userRepository.existsByEmail(validRequest.getEmail())).thenReturn(false);
        lenient().when(passwordEncoder.encode(validRequest.getContrasena())).thenReturn("hashedPassword");

        // Ejecutar
        UserRegistrationResponse response = userService.registerUser(validRequest);

        // Verificar
        assertNotNull(response);
        assertEquals(validRequest.getEmail(), response.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_ShouldThrowException_WhenUserAlreadyExists() {
        // Simular que el usuario ya existe en la BD
        lenient().when(userRepository.existsByEmail(validRequest.getEmail())).thenReturn(true);

        // Verificar que se lanza una excepción
        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(validRequest));

        // Verificar que el usuario no se guardó
        verify(userRepository, never()).save(any(User.class));
    }
}

