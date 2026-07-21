package co.edu.unicauca.piedraazul.pattern.state;

public class EstadoConfirmada extends EstadoConsulta {

    @Override
    public String getNombreEstado() {
        return "CONFIRMADA";
    }

    @Override
    public void iniciarAtencion(Consulta consulta) {
        System.out.println("La atención de la consulta ha iniciado.");
        consulta.cambiarEstado(new EstadoEnAtencion());
    }

    @Override
    public void cancelar(Consulta consulta) {
        System.out.println("La consulta confirmada fue cancelada correctamente.");
        consulta.cambiarEstado(new EstadoCancelada());
    }
}