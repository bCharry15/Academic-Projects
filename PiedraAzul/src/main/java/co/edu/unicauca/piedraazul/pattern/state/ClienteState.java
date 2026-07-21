package co.edu.unicauca.piedraazul.pattern.state;

public class ClienteState {

    public static void main(String[] args) {
        System.out.println("PRUEBA DEL PATRÓN STATE EN PIEDRAAZUL");
        System.out.println("============================================");

        Consulta consulta = new Consulta(1L, "Carlos Ramírez", "Dra. Laura Gómez");

        consulta.mostrarEstadoActual();

        System.out.println("Acción: iniciar atención sin confirmar");
        consulta.iniciarAtencion();
        consulta.mostrarEstadoActual();

        System.out.println("Acción: confirmar consulta");
        consulta.confirmar();
        consulta.mostrarEstadoActual();

        System.out.println("Acción: iniciar atención");
        consulta.iniciarAtencion();
        consulta.mostrarEstadoActual();

        System.out.println("Acción: cancelar consulta en atención");
        consulta.cancelar();
        consulta.mostrarEstadoActual();

        System.out.println("Acción: completar consulta");
        consulta.completar();
        consulta.mostrarEstadoActual();

        System.out.println("Acción: cancelar consulta completada");
        consulta.cancelar();
        consulta.mostrarEstadoActual();
    }
}