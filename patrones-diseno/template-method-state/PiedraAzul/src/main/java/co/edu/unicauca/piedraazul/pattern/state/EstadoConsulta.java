package co.edu.unicauca.piedraazul.pattern.state;

public abstract class EstadoConsulta {

    public abstract String getNombreEstado();

    public void confirmar(Consulta consulta) {
        mostrarTransicionNoPermitida("confirmar");
    }

    public void iniciarAtencion(Consulta consulta) {
        mostrarTransicionNoPermitida("iniciar atención");
    }

    public void completar(Consulta consulta) {
        mostrarTransicionNoPermitida("completar");
    }

    public void cancelar(Consulta consulta) {
        mostrarTransicionNoPermitida("cancelar");
    }

    protected void mostrarTransicionNoPermitida(String accion) {
        System.out.println("No se puede ejecutar la acción '" + accion + "' desde el estado " + getNombreEstado() + ".");
    }
}