package co.edu.unicauca.piedraazul.pattern.state;

public class EstadoEnAtencion extends EstadoConsulta {

    @Override
    public String getNombreEstado() {
        return "EN_ATENCION";
    }

    @Override
    public void completar(Consulta consulta) {
        System.out.println("La consulta fue completada correctamente.");
        consulta.cambiarEstado(new EstadoCompletada());
    }
}