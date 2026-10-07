
package co.edu.unicauca.distribuidos.core.capaControladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ClienteDTO;
import co.edu.unicauca.distribuidos.core.fachadaServices.services.IClienteService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200",  
 methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}) 
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;

	@GetMapping("/clientes") 
	public List<ClienteDTO> listarClientes() {
		return clienteService.findAll();
	}

	@GetMapping("/clientes/{id}")
	public ClienteDTO consultarCliente(@PathVariable Integer id) {
		ClienteDTO objCliente = null;
		objCliente = clienteService.findById(id);
		return objCliente; 
	}

	
	@GetMapping("/clientes2")
	public ClienteDTO consultarCliente2(@RequestParam Integer id) {
		ClienteDTO objCliente = null;
		objCliente = clienteService.findById(id);
		return objCliente;
	}

	@PostMapping("/clientes")
	public ClienteDTO crearCliente(@RequestBody ClienteDTO cliente) {
		ClienteDTO objCliente = null;
		objCliente = clienteService.save(cliente);
		return objCliente;
	}

	@PutMapping("/clientes/{id}")
	public ClienteDTO actualizarCliente(@RequestBody ClienteDTO cliente, @PathVariable Integer id) {
		ClienteDTO objCliente = null;
		ClienteDTO clienteActual = clienteService.findById(id);
		if (clienteActual != null) {
			objCliente = clienteService.update(id, cliente);
		}
		return objCliente;
	}

	@DeleteMapping("/clientes/{id}")
	public Boolean eliminarCliente(@PathVariable Integer id) {
		Boolean bandera = false;
		ClienteDTO clienteActual = clienteService.findById(id);
		if (clienteActual != null) {
			bandera = clienteService.delete(id);
		}
		return bandera;
	}

	@GetMapping("/clientes/codigo/{codigo}")
public ResponseEntity<Boolean> existeClienteCodigo(
        @PathVariable String codigo) {

    Boolean bandera = clienteService.existeClienteCodigo(codigo);

    ResponseEntity<Boolean> objRespuesta =
        new ResponseEntity<Boolean>(bandera, HttpStatus.OK);

    return objRespuesta;
}

}
