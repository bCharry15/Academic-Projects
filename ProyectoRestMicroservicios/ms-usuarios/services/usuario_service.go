package services

import (
	"ms-usuarios/dto"
	"ms-usuarios/entities"
	"ms-usuarios/repositories"
)

// UsuarioService es la fachada entre los controladores y el acceso a datos.
// Aquí se transforma DTO -> Entity y Entity -> DTO.
type UsuarioService struct {
	repo *repositories.UsuarioRepository
}

func NewUsuarioService(repo *repositories.UsuarioRepository) *UsuarioService {
	return &UsuarioService{repo: repo}
}

func (s *UsuarioService) Listar() []dto.UsuarioDTO {
	usuarios := s.repo.ListarTodos()
	resultado := make([]dto.UsuarioDTO, 0, len(usuarios))
	for _, u := range usuarios {
		resultado = append(resultado, toDTO(u))
	}
	return resultado
}

func (s *UsuarioService) Registrar(d dto.UsuarioDTO) (dto.UsuarioDTO, error) {
	guardado, err := s.repo.Guardar(toEntity(d))
	if err != nil {
		return dto.UsuarioDTO{}, err
	}
	return toDTO(guardado), nil
}

func toEntity(d dto.UsuarioDTO) entities.Usuario {
	return entities.Usuario{
		Cedula:    d.Cedula,
		Nombres:   d.Nombres,
		Apellidos: d.Apellidos,
	}
}

func toDTO(u entities.Usuario) dto.UsuarioDTO {
	return dto.UsuarioDTO{
		Cedula:    u.Cedula,
		Nombres:   u.Nombres,
		Apellidos: u.Apellidos,
	}
}
