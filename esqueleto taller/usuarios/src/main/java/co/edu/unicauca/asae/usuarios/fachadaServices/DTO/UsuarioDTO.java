package co.edu.unicauca.asae.usuarios.fachadaServices.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
	private Integer id;
	private String nombres;
	private String apellidos;
	private String tipoIdentificacion;
	private String noIdentificacion;	
	
}