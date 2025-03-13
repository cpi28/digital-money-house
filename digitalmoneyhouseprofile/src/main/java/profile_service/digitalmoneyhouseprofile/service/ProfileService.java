package profile_service.digitalmoneyhouseprofile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import profile_service.digitalmoneyhouseprofile.exception.UserNotFoundException;
import profile_service.digitalmoneyhouseprofile.model.User;
import profile_service.digitalmoneyhouseprofile.repository.UserRepository;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    public User obtenerUsuarioPorId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + id));
    }

    public User actualizarUsuario(Long id, User usuarioActualizado) {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + id));

        usuarioActualizado.setId(usuario.getId());
        return userRepository.save(usuarioActualizado);
    }

}
