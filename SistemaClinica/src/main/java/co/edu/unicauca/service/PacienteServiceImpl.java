package co.edu.unicauca.service;

import co.edu.unicauca.model.Paciente;
import co.edu.unicauca.model.Usuario;
import co.edu.unicauca.repository.IPacienteRepository;

import java.util.List;
import java.util.Optional;

public class PacienteServiceImpl implements IPacienteService {

    private final IPacienteRepository repository;

    public PacienteServiceImpl(IPacienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registrar(Paciente paciente, Usuario usuarioActivo) {
        validarAdmin(usuarioActivo);
        validarPacienteNoNull(paciente);
        validarCamposObligatorios(paciente);

        // Si tu repo soporta buscarPorCedula, valida duplicados:
        Optional<Paciente> existente = repository.buscarPorCedula(paciente.getCedula());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un paciente con la cédula: " + paciente.getCedula());
        }

        repository.guardar(paciente);
    }

    @Override
    public void actualizar(Paciente paciente, Usuario usuarioActivo) {
        validarAdmin(usuarioActivo); // <-- ESTO era lo que te faltaba para que el test pase
        validarPacienteNoNull(paciente);
        validarCamposObligatorios(paciente);

        repository.actualizar(paciente);
    }

    @Override
    public void eliminar(int id, Usuario usuarioActivo) {
        validarAdmin(usuarioActivo);
        repository.eliminar(id);
    }

    @Override
    public Optional<Paciente> buscarPorCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return Optional.empty();
        }
        return repository.buscarPorCedula(cedula.trim());
    }

    @Override
    public List<Paciente> listarTodos() {
        return repository.listarTodos();
    }

    // ==========================
    // Validaciones
    // ==========================

    private void validarAdmin(Usuario usuarioActivo) {
        if (usuarioActivo == null) {
            throw new SecurityException("Usuario no autenticado.");
        }
        if (usuarioActivo.getRol() != Usuario.Rol.ADMIN) {
            throw new SecurityException("Solo un ADMIN puede realizar esta operación.");
        }
    }

    private void validarPacienteNoNull(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente no puede ser null.");
        }
    }

    // Ajusta aquí los campos obligatorios reales que tu modelo exige
    private void validarCamposObligatorios(Paciente paciente) {
        if (esVacio(paciente.getCedula())) {
            throw new IllegalArgumentException("La cédula del paciente es obligatoria.");
        }
        if (esVacio(paciente.getNombre())) {
            throw new IllegalArgumentException("El nombre del paciente es obligatorio.");
        }
        if (esVacio(paciente.getApellido())) {
            throw new IllegalArgumentException("El apellido del paciente es obligatorio.");
        }
    }

    private boolean esVacio(String s) {
        return s == null || s.trim().isEmpty();
    }
}
