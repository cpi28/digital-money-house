package authentication_service.digitalmoneyhouseauth.service;

import authentication_service.digitalmoneyhouseauth.dto.LoginRequest;
import authentication_service.digitalmoneyhouseauth.dto.LoginResponse;
import authentication_service.digitalmoneyhouseauth.model.User;
import authentication_service.digitalmoneyhouseauth.repository.UserRepository;
import authentication_service.digitalmoneyhouseauth.security.JwtTokenProvider;
import authentication_service.digitalmoneyhouseauth.security.TokenBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenBlacklistService tokenBlacklistService;

    @Autowired
    public AuthService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, TokenBlacklistService tokenBlacklistService) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenBlacklistService = tokenBlacklistService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public LoginResponse authenticateUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtTokenProvider.createToken(user.getEmail());
        return new LoginResponse(token);
    }

    public void logout(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("El token no puede estar vacío");
        }

        tokenBlacklistService.invalidateToken(token);
    }
}

