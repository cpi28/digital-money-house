package user_registration_service.digitalmoneyhouse.userregistration.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user_registration_service.digitalmoneyhouse.userregistration.dto.UserRegistrationRequest;
import user_registration_service.digitalmoneyhouse.userregistration.dto.UserRegistrationResponse;
import user_registration_service.digitalmoneyhouse.userregistration.service.UserService;

/**
 * Controlador REST para el registro de usuarios.
 * Expone el endpoint /api/register para la creación de nuevos usuarios.
 */
@RestController
@RequestMapping("/api")
public class UserRegistrationController {

    private final UserService userService;

    @Autowired
    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Se espera recibir en el cuerpo de la solicitud un objeto con los datos requeridos (nombre, apellido, DNI,
     * email, teléfono y contraseña). La respuesta contiene los datos del usuario registrado (incluyendo ID, CVU y alias)
     * pero sin la contraseña.
     *
     * @param registrationRequest Datos del usuario a registrar.
     * @return Objeto con la información del usuario registrado.
     */
    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un nuevo usuario con CVU y alias generados automáticamente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRegistrationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (datos inválidos o usuario ya existente)",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody UserRegistrationRequest registrationRequest) {
        UserRegistrationResponse response = userService.registerUser(registrationRequest);
        return ResponseEntity.ok(response);
    }
}

