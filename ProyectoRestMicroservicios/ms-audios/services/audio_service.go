package services

import (
	"ms-audios/dto"
	"ms-audios/entities"
	"ms-audios/repositories"
)

// AudioService es la fachada: transforma DTO -> Entity y Entity -> DTO.
type AudioService struct {
	repo *repositories.AudioRepository
}

func NewAudioService(repo *repositories.AudioRepository) *AudioService {
	return &AudioService{repo: repo}
}

func (s *AudioService) Listar() []dto.AudioDTO {
	audios := s.repo.ListarTodos()
	resultado := make([]dto.AudioDTO, 0, len(audios))

	for _, a := range audios {
		resultado = append(resultado, toDTO(a))
	}

	return resultado
}

func (s *AudioService) Registrar(d dto.AudioDTO) (dto.AudioDTO, error) {
	guardado, err := s.repo.Guardar(toEntity(d))
	if err != nil {
		return dto.AudioDTO{}, err
	}

	return toDTO(guardado), nil
}

func toEntity(d dto.AudioDTO) entities.Audio {
	return entities.Audio{
		Titulo:   d.Titulo,
		Duracion: d.Duracion,
		Tipo:     d.Tipo,
	}
}

func toDTO(a entities.Audio) dto.AudioDTO {
	return dto.AudioDTO{
		Titulo:   a.Titulo,
		Duracion: a.Duracion,
		Tipo:     a.Tipo,
	}
}
