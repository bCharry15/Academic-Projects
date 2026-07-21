package co.edu.unicauca.piedraazul.agenda.pattern.builder;

import co.edu.unicauca.piedraazul.agenda.model.dto.CitaResponse;

/**
 * Builder abstracto para construir respuestas de citas paso a paso.
 * Define el contrato común que deben cumplir los builders concretos.
 */
public abstract class AbstractCitaResponseBuilder {

    protected CitaResponse citaResponse;

    public void crearNuevaCitaResponse() {
        this.citaResponse = new CitaResponse();
    }

    public CitaResponse getCitaResponse() {
        return citaResponse;
    }

    public abstract void buildId();

    public abstract void buildPaciente();

    public abstract void buildMedico();

    public abstract void buildFecha();

    public abstract void buildHora();

    public abstract void buildEstado();

    public abstract void buildObservacion();
}
