package co.edu.unicauca.piedraazul.pattern.template;

import co.edu.unicauca.piedraazul.model.dto.CrearCitaRequest;

public class ValidadorCitaPresencial extends ValidadorCitaTemplate {

    @Override
    protected void validarReglasEspecificas(CrearCitaRequest request, ResultadoValidacionCita resultado) {
        if (request.getObservacion() == null || request.getObservacion().isBlank()) {
            resultado.agregarError("Para una cita presencial se recomienda registrar una observación inicial.");
        }
    }
}