package co.edu.unicauca.piedraazul.pattern.state;

public class EstadoCompletada extends EstadoConsulta {

    @Override
    public String getNombreEstado() {
        return "COMPLETADA";
    }
}