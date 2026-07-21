package co.edu.unicauca.piedraazul.agenda.pattern.builder;

import co.edu.unicauca.piedraazul.agenda.model.dto.CitaResponse;

/**
 * Director del patrón Builder.
 * Controla el orden de construcción del objeto CitaResponse.
 */
public class DirectorCitaResponse {

    public CitaResponse construir(AbstractCitaResponseBuilder builder) {
        builder.crearNuevaCitaResponse();
        builder.buildId();
        builder.buildPaciente();
        builder.buildMedico();
        builder.buildFecha();
        builder.buildHora();
        builder.buildEstado();
        builder.buildObservacion();
        return builder.getCitaResponse();
    }
}
