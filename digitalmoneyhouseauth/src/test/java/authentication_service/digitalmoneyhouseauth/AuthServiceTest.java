package authentication_service.digitalmoneyhouseauth;

import authentication_service.digitalmoneyhouseauth.dto.LoginRequest;
import authentication_service.digitalmoneyhouseauth.dto.LoginResponse;
import authentication_service.digitalmoneyhouseauth.exception.InvalidCredentialsException;
import authentication_service.digitalmoneyhouseauth.model.User;
import authentication_service.digitalmoneyhouseauth.repository.UserRepository;
import authentication_service.digitalmoneyhouseauth.security.JwtTokenProvider;
import authentication_service.digitalmoneyhouseauth.security.TokenBlacklistService;
import authentication_service.digitalmoneyhouseauth.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private TokenBlacklistService tokenBlacklistService;

    @InjectMocks
    private AuthService authService;

    private LoginRequest validLoginRequest;
    private User mockUser;

    @BeforeEach
    void setUp() {
        validLoginRequest = new LoginRequest("juan.perez@example.com", "password123");

        mockUser = new User();
        mockUser.setEmail("juan.perez@example.com");
        mockUser.setContrasena(new BCryptPasswordEncoder().encode("password123"));
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtTokenProvider.createToken(mockUser.getEmail())).thenReturn("mocked-jwt-token");

        LoginResponse response = authService.authenticateUser(validLoginRequest);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
        verify(jwtTokenProvider, times(1)).createToken(mockUser.getEmail());
    }

    @Test
    void login_ShouldThrowInvalidCredentialsException_WhenAuthenticationFails() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new InvalidCredentialsException("Credenciales inválidas"));

        assertThrows(InvalidCredentialsException.class, () -> authService.authenticateUser(validLoginRequest));
    }

    @Test
    void logout_ShouldAddTokenToBlacklist() {
        String token = "mocked-jwt-token";

        authService.logout(token);

        verify(tokenBlacklistService, times(1)).invalidateToken(token);
    }
}

