
package co.edu.unicauca.asae.usuarios.fachadaServices.services;

import java.util.List;

import co.edu.unicauca.asae.usuarios.fachadaServices.DTO.UsuarioDTO;


public interface IUsuarioService {
	public List<UsuarioDTO> findAll();
	public UsuarioDTO findById(Integer id);
}
