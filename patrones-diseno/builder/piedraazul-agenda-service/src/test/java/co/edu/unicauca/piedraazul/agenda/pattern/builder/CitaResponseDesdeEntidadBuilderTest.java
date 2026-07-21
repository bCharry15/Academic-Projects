package co.edu.unicauca.piedraazul.agenda.pattern.builder;

import co.edu.unicauca.piedraazul.agenda.model.Cita;
import co.edu.unicauca.piedraazul.agenda.model.Medico;
import co.edu.unicauca.piedraazul.agenda.model.Paciente;
import co.edu.unicauca.piedraazul.agenda.model.dto.CitaResponse;
import co.edu.unicauca.piedraazul.agenda.model.enums.EstadoCita;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas unitarias - Builder abstracto de CitaResponse")
class CitaResponseDesdeEntidadBuilderTest {

    @Test
    @DisplayName("El director construye un CitaResponse usando clase abstracta y builder concreto")
    void directorConstruyeCitaResponseDesdeEntidad() {
        Paciente paciente = new Paciente();
        ReflectionTestUtils.setField(paciente, "id", 10L);
        paciente.setNombres("Ana");
        paciente.setApellidos("Pérez");

        Medico medico = new Medico();
        ReflectionTestUtils.setField(medico, "id", 5L);
        medico.setNombreCompleto("Dra. Magaly Torres");

        Cita cita = new Cita();
        ReflectionTestUtils.setField(cita, "id", 1L);
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setFecha(LocalDate.of(2026, 5, 20));
        cita.setHora(LocalTime.of(9, 0));
        cita.setEstado(EstadoCita.PROGRAMADA);
        cita.setObservacion("Primera valoración");

        AbstractCitaResponseBuilder builder = new CitaResponseDesdeEntidadBuilder(cita);
        DirectorCitaResponse director = new DirectorCitaResponse();

        CitaResponse response = director.construir(builder);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(10L, response.getPacienteId());
        assertEquals("Ana Pérez", response.getPaciente());
        assertEquals(5L, response.getMedicoId());
        assertEquals("Dra. Magaly Torres", response.getMedico());
        assertEquals(LocalDate.of(2026, 5, 20), response.getFecha());
        assertEquals(LocalTime.of(9, 0), response.getHora());
        assertEquals("PROGRAMADA", response.getEstado());
        assertEquals("Primera valoración", response.getObservacion());
    }

    @Test
    @DisplayName("El builder concreto valida que la cita no sea nula")
    void builderConCitaNulaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new CitaResponseDesdeEntidadBuilder(null));
    }
}
