package co.edu.unicauca.distribuidos.core.capaAccesoADatos.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClienteEntity {
private Integer id;
private String codigo;
private String nombre;
private String apellido;
private String email;
private Date createAt;

	public ClienteEntity() {

	}
}
