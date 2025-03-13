package dashboard_service.digitalmoneyhousedash.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para evitar restricciones en APIs públicas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/api/accounts/**"
                        ).permitAll() // Permitir acceso sin autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login.disable()) // Deshabilitar formulario de login por defecto
                .httpBasic(basic -> basic.disable()); // Deshabilitar autenticación básica

        return http.build();
    }
}

