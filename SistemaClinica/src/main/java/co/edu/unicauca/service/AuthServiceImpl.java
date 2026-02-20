/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.edu.unicauca.service;

/**
 *
 * @author jpuen
 */
import co.edu.unicauca.model.Usuario;
import co.edu.unicauca.repository.IUsuarioRepository;
import co.edu.unicauca.util.PasswordUtil;
import java.util.Optional;

public class AuthServiceImpl implements IAuthService {

    private final IUsuarioRepository usuarioRepository;

    public AuthServiceImpl(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<Usuario> login(String username, String password) {
        if (username == null || password == null) return Optional.empty();
        return usuarioRepository.buscarPorUsername(username)
                .filter(u -> PasswordUtil.verificar(password, u.getPasswordHash()));
    }

    @Override
    public void registrarUsuario(String username, String password, Usuario.Rol rol, Usuario admin) {
        if (admin == null || !admin.esAdmin()) {
            throw new SecurityException("Solo un ADMIN puede registrar nuevos usuarios.");
        }
        if (usuarioRepository.existeUsername(username)) {
            throw new IllegalArgumentException("El usuario '" + username + "' ya existe.");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }
        Usuario nuevo = new Usuario(0, username, PasswordUtil.hashear(password), rol);
        usuarioRepository.guardar(nuevo);
    }
}
