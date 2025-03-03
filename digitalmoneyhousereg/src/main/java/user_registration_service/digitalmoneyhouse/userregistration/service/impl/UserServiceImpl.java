package user_registration_service.digitalmoneyhouse.userregistration.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import user_registration_service.digitalmoneyhouse.userregistration.dto.UserRegistrationRequest;
import user_registration_service.digitalmoneyhouse.userregistration.dto.UserRegistrationResponse;
import user_registration_service.digitalmoneyhouse.userregistration.exception.UserAlreadyExistsException;
import user_registration_service.digitalmoneyhouse.userregistration.model.User;
import user_registration_service.digitalmoneyhouse.userregistration.repository.UserRepository;
import user_registration_service.digitalmoneyhouse.userregistration.service.UserService;
import user_registration_service.digitalmoneyhouse.userregistration.util.AliasGenerator;
import user_registration_service.digitalmoneyhouse.userregistration.util.CVUGenerator;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CVUGenerator cvuGenerator;
    private final AliasGenerator aliasGenerator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CVUGenerator cvuGenerator, AliasGenerator aliasGenerator) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.cvuGenerator = cvuGenerator;
        this.aliasGenerator = aliasGenerator;
    }

    @Override
    public UserRegistrationResponse registerUser(UserRegistrationRequest request) {
        // Verificar si el usuario ya está registrado
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("El usuario con email " + request.getEmail() + " ya está registrado.");
        }

        // Crear un nuevo usuario
        User user = new User();
        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setDni(request.getDni());
        user.setEmail(request.getEmail());
        user.setTelefono(request.getTelefono());
        user.setContrasena(passwordEncoder.encode(request.getContrasena())); // Encriptar la contraseña
        user.setCvu(cvuGenerator.generateCVU());
        user.setAlias(aliasGenerator.generateAlias());

        // Guardar en la base de datos
        User savedUser = userRepository.save(user);

        // Crear la respuesta sin la contraseña
        return new UserRegistrationResponse(
                savedUser.getId(),
                savedUser.getNombre(),
                savedUser.getApellido(),
                savedUser.getDni(),
                savedUser.getEmail(),
                savedUser.getTelefono(),
                savedUser.getCvu(),
                savedUser.getAlias()
        );
    }
}

