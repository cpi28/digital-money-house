package profile_service.digitalmoneyhouseprofile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import profile_service.digitalmoneyhouseprofile.exception.UserNotFoundException;
import profile_service.digitalmoneyhouseprofile.model.User;
import profile_service.digitalmoneyhouseprofile.repository.UserRepository;
import profile_service.digitalmoneyhouseprofile.service.ProfileService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProfileServiceTest {

    @Mock
    private UserRepository usuarioRepository;

    @InjectMocks
    private ProfileService perfilService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerUsuarioPorId_UsuarioEncontrado() {
        // Arrange
        Long id = 1L;
        User usuario = new User();
        usuario.setId(id);
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        // Act
        User resultado = perfilService.obtenerUsuarioPorId(id);

        // Assert
        assertEquals(usuario, resultado);
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    public void testObtenerUsuarioPorId_UsuarioNoEncontrado() {
        // Arrange
        Long id = 1L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> perfilService.obtenerUsuarioPorId(id));
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    public void testActualizarUsuario_UsuarioEncontrado() {
        // Arrange
        Long id = 1L;
        User usuarioExistente = new User();
        usuarioExistente.setId(id);
        User usuarioActualizado = new User();
        usuarioActualizado.setId(id);
        usuarioActualizado.setNombre("Nuevo Nombre");
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(usuarioActualizado)).thenReturn(usuarioActualizado);

        // Act
        User resultado = perfilService.actualizarUsuario(id, usuarioActualizado);

        // Assert
        assertEquals(usuarioActualizado, resultado);
        verify(usuarioRepository, times(1)).findById(id);
        verify(usuarioRepository, times(1)).save(usuarioActualizado);
    }

    @Test
    public void testActualizarUsuario_UsuarioNoEncontrado() {
        // Arrange
        Long id = 1L;
        User usuarioActualizado = new User();
        usuarioActualizado.setId(id);
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> perfilService.actualizarUsuario(id, usuarioActualizado));
        verify(usuarioRepository, times(1)).findById(id);
        verify(usuarioRepository, never()).save(any());
    }
}
