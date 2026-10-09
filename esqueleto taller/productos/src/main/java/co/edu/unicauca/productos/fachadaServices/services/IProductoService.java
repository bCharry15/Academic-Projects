
package co.edu.unicauca.productos.fachadaServices.services;

import java.util.List;
import co.edu.unicauca.productos.fachadaServices.DTO.ProductoDTO;


public interface IProductoService {

	public List<ProductoDTO> findAll();

	public ProductoDTO findById(Integer id);
}
