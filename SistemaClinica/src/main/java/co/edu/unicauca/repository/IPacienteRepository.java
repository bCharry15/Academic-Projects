package co.edu.unicauca.repository;

import co.edu.unicauca.model.Paciente;
import java.util.List;
import java.util.Optional;

public interface IPacienteRepository {
    void guardar(Paciente paciente);
    void actualizar(Paciente paciente);
    void eliminar(int id);
    Optional<Paciente> buscarPorId(int id);
    Optional<Paciente> buscarPorCedula(String cedula);
    List<Paciente> listarTodos();
}