
package co.edu.unicauca.productos.fachadaServices.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import co.edu.unicauca.productos.fachadaServices.DTO.ProductoDTO;

@Service
public class ProductoServiceImpl implements IProductoService {

	private final Map<Integer, ProductoDTO> productos = new HashMap<>();

    public ProductoServiceImpl()
	{	
        productos.put(1, new ProductoDTO(1, "Laptop","101","ASEO", 1200, new Date()));
        productos.put(2, new ProductoDTO(2, "Lapiz","102", "PAPELERIA",800,new Date()));
    }


	@Override
	public List<ProductoDTO> findAll() {
		System.out.println("Listando productos");
		return this.productos.values().stream().toList();		
	}

	@Override
	public ProductoDTO findById(Integer id) {
		System.out.println("Consultando producto por id");
		return productos.getOrDefault(id, new ProductoDTO(0, "Producto No encontrado","","",0,new Date()));
	}

	
}
