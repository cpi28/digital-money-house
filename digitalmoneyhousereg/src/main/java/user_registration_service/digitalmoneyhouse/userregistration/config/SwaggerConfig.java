package user_registration_service.digitalmoneyhouse.userregistration.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger/OpenAPI para documentar la API.
 * Se utiliza springdoc-openapi-ui, que permite generar una
 * documentación interactiva de la API.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Registration API")
                        .version("1.0")
                        .description("API para el servicio de registro de usuarios en Digital Money House"));
    }
}

