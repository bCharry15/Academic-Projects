package co.edu.unicauca.piedraazul.pattern.template;

import java.time.LocalDate;
import java.time.LocalTime;

import co.edu.unicauca.piedraazul.model.dto.CrearCitaRequest;

public class ClienteTemplateMethod {

    public static void main(String[] args) {
        CrearCitaRequest citaVirtual = new CrearCitaRequest();
        citaVirtual.setNumeroDocumento("1002456789");
        citaVirtual.setTipoDocumento("CC");
        citaVirtual.setNombres("Carlos");
        citaVirtual.setApellidos("Ramírez");
        citaVirtual.setCelular("3104567890");
        citaVirtual.setGenero("MASCULINO");
        citaVirtual.setMedicoId(1L);
        citaVirtual.setFecha(LocalDate.now().plusDays(1));
        citaVirtual.setHora(LocalTime.of(10, 30));
        citaVirtual.setCorreo("carlos.ramirez@gmail.com");
        citaVirtual.setObservacion("Paciente solicita valoración general.");

        ValidadorCitaTemplate validadorVirtual = new ValidadorCitaVirtual();
        ResultadoValidacionCita resultadoVirtual = validadorVirtual.validar(citaVirtual);

        System.out.println("VALIDACIÓN DE CITA VIRTUAL");
        resultadoVirtual.imprimirResultado();

        System.out.println("------------------------------------");

        CrearCitaRequest citaPresencial = new CrearCitaRequest();
        citaPresencial.setNumeroDocumento("1003987654");
        citaPresencial.setTipoDocumento("CC");
        citaPresencial.setNombres("Laura");
        citaPresencial.setApellidos("Gómez");
        citaPresencial.setCelular("3159876543");
        citaPresencial.setGenero("FEMENINO");
        citaPresencial.setMedicoId(2L);
        citaPresencial.setFecha(LocalDate.now().plusDays(2));
        citaPresencial.setHora(LocalTime.of(15, 0));
        citaPresencial.setObservacion("Paciente asiste por control.");

        ValidadorCitaTemplate validadorPresencial = new ValidadorCitaPresencial();
        ResultadoValidacionCita resultadoPresencial = validadorPresencial.validar(citaPresencial);

        System.out.println("VALIDACIÓN DE CITA PRESENCIAL");
        resultadoPresencial.imprimirResultado();
    }
}