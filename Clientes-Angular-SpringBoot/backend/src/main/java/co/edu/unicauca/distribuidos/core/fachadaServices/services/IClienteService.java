
package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import java.util.List;

import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ClienteDTO;

public interface IClienteService {

	public List<ClienteDTO> findAll();

	public ClienteDTO findById(Integer id);

	public ClienteDTO save(ClienteDTO cliente);

	public ClienteDTO update(Integer id, ClienteDTO cliente);

	public boolean delete(Integer id);

	public boolean existeClienteCodigo(String codigo);
}
