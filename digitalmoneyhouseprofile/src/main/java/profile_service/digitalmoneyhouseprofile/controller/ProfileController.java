package profile_service.digitalmoneyhouseprofile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import profile_service.digitalmoneyhouseprofile.model.User;
import profile_service.digitalmoneyhouseprofile.service.ProfileService;

@RestController
@RequestMapping("/users")
public class ProfileController {

    @Autowired
    private ProfileService perfilService;

    @GetMapping("/{id}")
    public ResponseEntity<User> obtenerUsuario(@PathVariable Long id) {
        User usuario = perfilService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> actualizarUsuario(@PathVariable Long id, @RequestBody User usuarioActualizado) {
        User usuario = perfilService.actualizarUsuario(id, usuarioActualizado);
        return ResponseEntity.ok(usuario);
    }
}
