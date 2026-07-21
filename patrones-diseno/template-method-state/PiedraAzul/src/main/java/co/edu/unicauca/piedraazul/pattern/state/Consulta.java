package co.edu.unicauca.piedraazul.pattern.state;

public class Consulta {

    private Long id;
    private String paciente;
    private String medico;
    private EstadoConsulta estado;

    public Consulta(Long id, String paciente, String medico) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.estado = new EstadoProgramada();
    }

    public void confirmar() {
        estado.confirmar(this);
    }

    public void iniciarAtencion() {
        estado.iniciarAtencion(this);
    }

    public void completar() {
        estado.completar(this);
    }

    public void cancelar() {
        estado.cancelar(this);
    }

    public void cambiarEstado(EstadoConsulta nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void mostrarEstadoActual() {
        System.out.println("Consulta #" + id + " | Paciente: " + paciente + " | Médico: " + medico);
        System.out.println("Estado actual: " + estado.getNombreEstado());
        System.out.println("--------------------------------------------");
    }

    public Long getId() {
        return id;
    }

    public String getPaciente() {
        return paciente;
    }

    public String getMedico() {
        return medico;
    }

    public EstadoConsulta getEstado() {
        return estado;
    }
}