package co.edu.unicauca.piedraazul.pattern.template;

import java.time.LocalDate;
import java.time.LocalTime;

import co.edu.unicauca.piedraazul.model.dto.CrearCitaRequest;

public abstract class ValidadorCitaTemplate {

    public final ResultadoValidacionCita validar(CrearCitaRequest request) {
        ResultadoValidacionCita resultado = new ResultadoValidacionCita();

        validarPaciente(request, resultado);
        validarMedico(request, resultado);
        validarFecha(request, resultado);
        validarHora(request, resultado);
        validarReglasEspecificas(request, resultado);

        return resultado;
    }

    private void validarPaciente(CrearCitaRequest request, ResultadoValidacionCita resultado) {
        if (request.getNumeroDocumento() == null || request.getNumeroDocumento().isBlank()) {
            resultado.agregarError("El número de documento del paciente es obligatorio.");
        }

        if (request.getNombres() == null || request.getNombres().isBlank()) {
            resultado.agregarError("Los nombres del paciente son obligatorios.");
        }

        if (request.getApellidos() == null || request.getApellidos().isBlank()) {
            resultado.agregarError("Los apellidos del paciente son obligatorios.");
        }

        if (request.getCelular() == null || request.getCelular().isBlank()) {
            resultado.agregarError("El celular del paciente es obligatorio.");
        }
    }

    private void validarMedico(CrearCitaRequest request, ResultadoValidacionCita resultado) {
        if (request.getMedicoId() == null) {
            resultado.agregarError("Debe seleccionar un médico para la cita.");
        }
    }

    private void validarFecha(CrearCitaRequest request, ResultadoValidacionCita resultado) {
        if (request.getFecha() == null) {
            resultado.agregarError("La fecha de la cita es obligatoria.");
            return;
        }

        if (request.getFecha().isBefore(LocalDate.now())) {
            resultado.agregarError("La fecha de la cita no puede ser anterior a la fecha actual.");
        }
    }

    private void validarHora(CrearCitaRequest request, ResultadoValidacionCita resultado) {
        if (request.getHora() == null) {
            resultado.agregarError("La hora de la cita es obligatoria.");
            return;
        }

        if (request.getHora().isBefore(LocalTime.of(6, 0)) || request.getHora().isAfter(LocalTime.of(20, 0))) {
            resultado.agregarError("La hora de la cita debe estar entre las 6:00 a.m. y las 8:00 p.m.");
        }
    }

    protected abstract void validarReglasEspecificas(CrearCitaRequest request, ResultadoValidacionCita resultado);
}