package co.edu.unicauca.piedraazul.pattern.state;

public class EstadoCancelada extends EstadoConsulta {

    @Override
    public String getNombreEstado() {
        return "CANCELADA";
    }
}