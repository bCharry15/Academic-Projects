package repositories

import (
	"errors"
	"sync"

	"ms-audios/entities"
)

var ErrAudioDuplicado = errors.New("ya existe un audio con ese título")

// AudioRepository almacena los audios en RAM mediante una lista.
type AudioRepository struct {
	mu     sync.RWMutex
	audios []entities.Audio
}

func NewAudioRepository() *AudioRepository {
	return &AudioRepository{audios: make([]entities.Audio, 0)}
}

func (r *AudioRepository) ListarTodos() []entities.Audio {
	r.mu.RLock()
	defer r.mu.RUnlock()

	copia := make([]entities.Audio, len(r.audios))
	copy(copia, r.audios)
	return copia
}

func (r *AudioRepository) Guardar(a entities.Audio) (entities.Audio, error) {
	r.mu.Lock()
	defer r.mu.Unlock()

	for _, existente := range r.audios {
		if existente.Titulo == a.Titulo {
			return entities.Audio{}, ErrAudioDuplicado
		}
	}

	r.audios = append(r.audios, a)
	return a, nil
}
