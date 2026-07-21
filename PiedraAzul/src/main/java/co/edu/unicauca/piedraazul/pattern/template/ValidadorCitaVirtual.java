package co.edu.unicauca.piedraazul.pattern.template;

import co.edu.unicauca.piedraazul.model.dto.CrearCitaRequest;

public class ValidadorCitaVirtual extends ValidadorCitaTemplate {

    @Override
    protected void validarReglasEspecificas(CrearCitaRequest request, ResultadoValidacionCita resultado) {
        if (request.getCorreo() == null || request.getCorreo().isBlank()) {
            resultado.agregarError("Para una cita virtual el correo electrónico del paciente es obligatorio.");
            return;
        }

        if (!request.getCorreo().contains("@")) {
            resultado.agregarError("El correo electrónico del paciente no tiene un formato válido.");
        }
    }
}