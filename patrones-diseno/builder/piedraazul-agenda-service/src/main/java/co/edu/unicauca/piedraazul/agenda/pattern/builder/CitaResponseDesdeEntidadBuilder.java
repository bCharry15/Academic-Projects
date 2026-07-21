package co.edu.unicauca.piedraazul.agenda.pattern.builder;

import co.edu.unicauca.piedraazul.agenda.model.Cita;
import co.edu.unicauca.piedraazul.agenda.model.Medico;
import co.edu.unicauca.piedraazul.agenda.model.Paciente;

/**
 * Builder concreto que construye un CitaResponse a partir de una entidad Cita.
 */
public class CitaResponseDesdeEntidadBuilder extends AbstractCitaResponseBuilder {

    private final Cita cita;

    public CitaResponseDesdeEntidadBuilder(Cita cita) {
        if (cita == null) {
            throw new IllegalArgumentException("La cita no puede ser nula");
        }
        this.cita = cita;
    }

    @Override
    public void buildId() {
        citaResponse.setId(cita.getId());
    }

    @Override
    public void buildPaciente() {
        Paciente paciente = cita.getPaciente();
        if (paciente != null) {
            citaResponse.setPacienteId(paciente.getId());
            citaResponse.setPaciente(paciente.getNombreCompleto());
        }
    }

    @Override
    public void buildMedico() {
        Medico medico = cita.getMedico();
        if (medico != null) {
            citaResponse.setMedicoId(medico.getId());
            citaResponse.setMedico(medico.getNombreCompleto());
        }
    }

    @Override
    public void buildFecha() {
        citaResponse.setFecha(cita.getFecha());
    }

    @Override
    public void buildHora() {
        citaResponse.setHora(cita.getHora());
    }

    @Override
    public void buildEstado() {
        if (cita.getEstado() != null) {
            citaResponse.setEstado(cita.getEstado().name());
        }
    }

    @Override
    public void buildObservacion() {
        citaResponse.setObservacion(cita.getObservacion());
    }
}
