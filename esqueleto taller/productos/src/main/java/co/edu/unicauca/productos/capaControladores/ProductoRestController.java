
package co.edu.unicauca.productos.capaControladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.productos.fachadaServices.DTO.ProductoDTO;
import co.edu.unicauca.productos.fachadaServices.services.IProductoService;


@RestController
@RequestMapping("/api")
public class ProductoRestController {

	@Autowired
	private IProductoService productoService;

	@GetMapping("/productos")
	public ResponseEntity<?> listarProductos(@RequestHeader(value = "X-Gateway-Passed", required = false )String gatewayHeader){
	if (gatewayHeader == null || !gatewayHeader.equals(	"true")){
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
		.body("Acceso denegado desde el controlador. Debe pasar por el API gateway.");

	}

	return ResponseEntity.ok(productoService.findAll());
	}

	@GetMapping("/productos/{id}")
	public ProductoDTO consultarProducto(@PathVariable Integer id) {
		ProductoDTO objProducto = null;
		objProducto = productoService.findById(id);
		return objProducto;
	}

	

}
