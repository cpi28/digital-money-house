package dashboard_service.digitalmoneyhousedash.config;


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
                        .title("User Dashboard API")
                        .version("1.0")
                        .description("API para el servicio de consulta de saldo y movimientos de la cuenta de usuarios en Digital Money House"));
    }
}
