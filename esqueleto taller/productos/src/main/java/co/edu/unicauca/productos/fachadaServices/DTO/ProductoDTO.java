package co.edu.unicauca.productos.fachadaServices.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
	private Integer id;
	private String nombre;
	private String codigo;
	private String tipo;
	private float precio;	
	private Date createAt;
	
}