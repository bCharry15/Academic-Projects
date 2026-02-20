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
import java.util.Optional;
public interface IAuthService {
    Optional<Usuario> login(String username, String password);
    void registrarUsuario(String username, String password, Usuario.Rol rol, Usuario admin);
}
