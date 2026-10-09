package co.edu.unicauca.asae.usuarios.capaControladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.asae.usuarios.fachadaServices.DTO.UsuarioDTO;
import co.edu.unicauca.asae.usuarios.fachadaServices.services.IUsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioRestController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public UsuarioDTO consultarUsuario(@PathVariable Integer id) {
        UsuarioDTO objProducto = null;
        objProducto = usuarioService.findById(id);
        return objProducto;
    }
}