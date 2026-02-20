package co.edu.unicauca.service;

import co.edu.unicauca.model.Usuario;
import co.edu.unicauca.repository.IUsuarioRepository;
import co.edu.unicauca.util.PasswordUtil;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceImplTest {
    @Test
    void loginCorrectoDebeRetornarUsuario() {
        IUsuarioRepository repo = new FakeUsuarioRepository();
        IAuthService auth = new AuthServiceImpl(repo);

        Optional<Usuario> resultado = auth.login("admin", "admin123");

        assertTrue(resultado.isPresent(), "Debe autenticarse con credenciales correctas");
        assertEquals("admin", resultado.get().getUsername());
        assertEquals(Usuario.Rol.ADMIN, resultado.get().getRol());
    }

    @Test
    void loginConPasswordIncorrectaDebeRetornarVacio() {
        IUsuarioRepository repo = new FakeUsuarioRepository();
        IAuthService auth = new AuthServiceImpl(repo);

        Optional<Usuario> resultado = auth.login("admin", "mala");

        assertTrue(resultado.isEmpty(), "Con contraseña incorrecta debe retornar Optional.empty()");
    }

    @Test
    void loginConUsuarioInexistenteDebeRetornarVacio() {
        IUsuarioRepository repo = new FakeUsuarioRepository();
        IAuthService auth = new AuthServiceImpl(repo);

        Optional<Usuario> resultado = auth.login("noexiste", "x");

        assertTrue(resultado.isEmpty(), "Si el usuario no existe debe retornar Optional.empty()");
    }

    @Test
    void loginConNullsDebeRetornarVacio() {
        IUsuarioRepository repo = new FakeUsuarioRepository();
        IAuthService auth = new AuthServiceImpl(repo);

        assertTrue(auth.login(null, "x").isEmpty(), "Username null debe retornar Optional.empty()");
        assertTrue(auth.login("admin", null).isEmpty(), "Password null debe retornar Optional.empty()");
    }

    @Test
    void registrarUsuarioComoAdminDebeGuardar() {
        FakeUsuarioRepository repo = new FakeUsuarioRepository();
        IAuthService auth = new AuthServiceImpl(repo);

        Usuario admin = repo.getAdmin();

        assertDoesNotThrow(() ->
                auth.registrarUsuario("nuevo", "nuevo123", Usuario.Rol.USER, admin)
        );

        assertTrue(repo.existeUsername("nuevo"), "El usuario nuevo debe quedar registrado en el repositorio fake");
    }

    @Test
    void registrarUsuarioSoloAdminPuedeRegistrar() {
        FakeUsuarioRepository repo = new FakeUsuarioRepository();
        IAuthService auth = new AuthServiceImpl(repo);

        Usuario admin = repo.getAdmin();
        Usuario user = repo.crearUsuarioBasico("user", "user123", Usuario.Rol.USER);

        // ADMIN sí puede
        assertDoesNotThrow(() ->
                auth.registrarUsuario("nuevo", "nuevo123", Usuario.Rol.USER, admin)
        );

        // USER no puede
        assertThrows(SecurityException.class, () ->
                auth.registrarUsuario("otro", "otro123", Usuario.Rol.USER, user)
        );
    }

    @Test
    void registrarUsuarioDebeFallarSiAdminEsNull() {
        FakeUsuarioRepository repo = new FakeUsuarioRepository();
        IAuthService auth = new AuthServiceImpl(repo);

        assertThrows(SecurityException.class, () ->
                auth.registrarUsuario("nuevo", "nuevo123", Usuario.Rol.USER, null)
        );
    }

    @Test
    void registrarUsuarioNoDebePermitirUsernameRepetido() {
        FakeUsuarioRepository repo = new FakeUsuarioRepository();
        IAuthService auth = new AuthServiceImpl(repo);

        Usuario admin = repo.getAdmin();

        // "admin" ya existe en el fake
        assertThrows(IllegalArgumentException.class, () ->
                auth.registrarUsuario("admin", "admin123", Usuario.Rol.USER, admin)
        );
    }

    @Test
    void registrarUsuarioDebeValidarPasswordMinima() {
        FakeUsuarioRepository repo = new FakeUsuarioRepository();
        IAuthService auth = new AuthServiceImpl(repo);

        Usuario admin = repo.getAdmin();

        // password muy corta (<6) según tu AuthServiceImpl
        assertThrows(IllegalArgumentException.class, () ->
                auth.registrarUsuario("nuevo", "123", Usuario.Rol.USER, admin)
        );
    }

    @Test
    void registrarUsuarioDebeFallarSiPasswordEsNull() {
        FakeUsuarioRepository repo = new FakeUsuarioRepository();
        IAuthService auth = new AuthServiceImpl(repo);

        Usuario admin = repo.getAdmin();

        assertThrows(IllegalArgumentException.class, () ->
                auth.registrarUsuario("nuevo", null, Usuario.Rol.USER, admin)
        );
    }

    // =========================
    // Fake repo SOLO para tests
    // =========================

    static class FakeUsuarioRepository implements IUsuarioRepository {

        private final java.util.Map<String, Usuario> data = new java.util.HashMap<>();
        private final Usuario admin;

        FakeUsuarioRepository() {
            admin = crearUsuarioBasico("admin", "admin123", Usuario.Rol.ADMIN);
            data.put("admin", admin);
        }

        Usuario getAdmin() {
            return admin;
        }

        Usuario crearUsuarioBasico(String username, String passwordPlano, Usuario.Rol rol) {
            String hash = PasswordUtil.hashear(passwordPlano);

            Usuario u = new Usuario();
            u.setUsername(username);
            u.setPasswordHash(hash);
            u.setRol(rol);

            return u;
        }

        @Override
        public void guardar(Usuario usuario) {
            data.put(usuario.getUsername(), usuario);
        }

        @Override
        public Optional<Usuario> buscarPorUsername(String username) {
            return Optional.ofNullable(data.get(username));
        }

        @Override
        public boolean existeUsername(String username) {
            return data.containsKey(username);
        }
    }
}
