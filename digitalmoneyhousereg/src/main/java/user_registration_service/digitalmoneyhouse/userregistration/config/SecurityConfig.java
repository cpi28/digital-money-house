package user_registration_service.digitalmoneyhouse.userregistration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de Spring Security.
 * En este ejemplo se deshabilita CSRF (para simplificar) y se
 * permite el acceso sin autenticación a los endpoints de registro, login y a los recursos de Swagger.
 * Los demás endpoints requieren autenticación.
 *
 * Nota: Para la implementación completa de JWT, se deberá incluir
 * filtros y manejadores específicos para la validación del token.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/api/register",
                                "/api/login",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}

