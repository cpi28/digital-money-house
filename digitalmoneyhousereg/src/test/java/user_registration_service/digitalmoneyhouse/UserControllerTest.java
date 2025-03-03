package user_registration_service.digitalmoneyhouse;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import user_registration_service.digitalmoneyhouse.userregistration.controller.UserRegistrationController;
import user_registration_service.digitalmoneyhouse.userregistration.dto.UserRegistrationRequest;
import user_registration_service.digitalmoneyhouse.userregistration.dto.UserRegistrationResponse;
import user_registration_service.digitalmoneyhouse.userregistration.exception.GlobalExceptionHandler;
import user_registration_service.digitalmoneyhouse.userregistration.exception.UserAlreadyExistsException;
import user_registration_service.digitalmoneyhouse.userregistration.service.UserService;
import user_registration_service.digitalmoneyhouse.userregistration.service.impl.UserServiceImpl;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserRegistrationController userController;

    @Test
    void registerUser_ShouldReturn200_WhenSuccessful() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        UserRegistrationRequest request = new UserRegistrationRequest(
                "Juan", "Pérez", "12345678",
                "juan.perez@example.com", "+541112345678",
                "password123"
        );

        UserRegistrationResponse response = new UserRegistrationResponse(
                "juan.perez@example.com"
        );

        when(userService.registerUser(any(UserRegistrationRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "nombre": "Juan",
                            "apellido": "Pérez",
                            "dni": "12345678",
                            "email": "juan.perez@example.com",
                            "telefono": "+541112345678",
                            "contrasena": "password123"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("juan.perez@example.com"));

        verify(userService, times(1)).registerUser(any(UserRegistrationRequest.class));
    }

    @Test
    void registerUser_ShouldReturn400_WhenUserAlreadyExists() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        when(userService.registerUser(any(UserRegistrationRequest.class)))
                .thenThrow(new UserAlreadyExistsException("El usuario ya está registrado"));

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "nombre": "Juan",
                            "apellido": "Pérez",
                            "dni": "12345678",
                            "email": "juan.perez@example.com",
                            "telefono": "+541112345678",
                            "contrasena": "password123"
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("El usuario ya está registrado"));;

        verify(userService, times(1)).registerUser(any(UserRegistrationRequest.class));
    }
}

