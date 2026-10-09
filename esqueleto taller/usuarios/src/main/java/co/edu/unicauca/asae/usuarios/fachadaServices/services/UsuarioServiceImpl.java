package co.edu.unicauca.asae.usuarios.fachadaServices.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import co.edu.unicauca.asae.usuarios.fachadaServices.DTO.UsuarioDTO;


@Service
public class UsuarioServiceImpl implements IUsuarioService {

	private final Map<Integer, UsuarioDTO> usuarios = new HashMap<>();

    public UsuarioServiceImpl()
	{	
        usuarios.put(1, new UsuarioDTO(1, "Juan", "Perez","CC","1061"));
        usuarios.put(2, new UsuarioDTO(2, "Andres", "Sandoval","CC","1062"));
       
    }


	@Override
	public List<UsuarioDTO> findAll() {
		return this.usuarios.values().stream().toList();		
	}

	@Override
	public UsuarioDTO findById(Integer id) {
		return usuarios.getOrDefault(id, new UsuarioDTO(0, "Usuario No encontrado","","",""));
	}

	
}
