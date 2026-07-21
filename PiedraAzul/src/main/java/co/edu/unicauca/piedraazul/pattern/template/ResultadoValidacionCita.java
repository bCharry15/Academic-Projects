package co.edu.unicauca.piedraazul.pattern.template;

import java.util.ArrayList;
import java.util.List;

public class ResultadoValidacionCita {

    private boolean valido;
    private final List<String> errores;

    public ResultadoValidacionCita() {
        this.valido = true;
        this.errores = new ArrayList<>();
    }

    public boolean esValido() {
        return valido;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void agregarError(String error) {
        this.valido = false;
        this.errores.add(error);
    }

    public void imprimirResultado() {
        if (valido) {
            System.out.println("La cita cumple con todas las validaciones.");
        } else {
            System.out.println("La cita no cumple con las validaciones:");
            for (String error : errores) {
                System.out.println("- " + error);
            }
        }
    }
}