package co.edu.unicauca.piedraazul.pattern.state;

public class EstadoProgramada extends EstadoConsulta {

    @Override
    public String getNombreEstado() {
        return "PROGRAMADA";
    }

    @Override
    public void confirmar(Consulta consulta) {
        System.out.println("La consulta fue confirmada correctamente.");
        consulta.cambiarEstado(new EstadoConfirmada());
    }

    @Override
    public void cancelar(Consulta consulta) {
        System.out.println("La consulta programada fue cancelada correctamente.");
        consulta.cambiarEstado(new EstadoCancelada());
    }
}