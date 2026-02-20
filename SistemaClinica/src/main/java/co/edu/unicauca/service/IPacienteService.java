/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.edu.unicauca.service;

/**
 *
 * @author jpuen
 */
import co.edu.unicauca.model.Paciente;
import co.edu.unicauca.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface IPacienteService {
    void registrar(Paciente paciente, Usuario usuarioActivo);
    void actualizar(Paciente paciente, Usuario usuarioActivo);
    void eliminar(int id, Usuario usuarioActivo);
    Optional<Paciente> buscarPorCedula(String cedula);
    List<Paciente> listarTodos();
}
