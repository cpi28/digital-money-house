package authentication_service.digitalmoneyhouseauth.controller;


import authentication_service.digitalmoneyhouseauth.dto.LoginRequest;
import authentication_service.digitalmoneyhouseauth.dto.LoginResponse;
import authentication_service.digitalmoneyhouseauth.security.TokenBlacklistService;
import authentication_service.digitalmoneyhouseauth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private final TokenBlacklistService tokenBlacklistService;

    @Autowired
    public AuthController(AuthService authService, TokenBlacklistService tokenBlacklistService) {
        this.authService = authService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    /*@PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logout exitoso (JWT manejado por cliente)");
    }*/

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            tokenBlacklistService.invalidateToken(token);
            return ResponseEntity.ok("Logout exitoso");
        }
        return ResponseEntity.badRequest().body("No se encontró token en la solicitud");
    }
}

