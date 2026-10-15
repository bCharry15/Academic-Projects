package services

import (
	"ms-productos/dto"
	"ms-productos/entities"
	"ms-productos/repositories"
)

// ProductoService es la fachada entre los controladores y el acceso a datos.
// Aquí se transforma DTO -> Entity y Entity -> DTO.
type ProductoService struct {
	repo *repositories.ProductoRepository
}

func NewProductoService(repo *repositories.ProductoRepository) *ProductoService {
	return &ProductoService{repo: repo}
}

func (s *ProductoService) Listar() []dto.ProductoDTO {
	productos := s.repo.ListarTodos()
	resultado := make([]dto.ProductoDTO, 0, len(productos))
	for _, p := range productos {
		resultado = append(resultado, toDTO(p))
	}
	return resultado
}

func (s *ProductoService) Registrar(d dto.ProductoDTO) (dto.ProductoDTO, error) {
	guardado, err := s.repo.Guardar(toEntity(d))
	if err != nil {
		return dto.ProductoDTO{}, err
	}
	return toDTO(guardado), nil
}

func toEntity(d dto.ProductoDTO) entities.Producto {
	return entities.Producto{
		Codigo: d.Codigo,
		Nombre: d.Nombre,
		Precio: d.Precio,
		Stock:  d.Stock,
	}
}

func toDTO(p entities.Producto) dto.ProductoDTO {
	return dto.ProductoDTO{
		Codigo: p.Codigo,
		Nombre: p.Nombre,
		Precio: p.Precio,
		Stock:  p.Stock,
	}
}
