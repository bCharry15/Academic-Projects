package repositories

import (
	"errors"
	"sync"

	"ms-usuarios/entities"
)

var ErrUsuarioDuplicado = errors.New("ya existe un usuario con esa cédula")

// UsuarioRepository almacena los usuarios en RAM mediante una lista (slice).
type UsuarioRepository struct {
	mu       sync.RWMutex
	usuarios []entities.Usuario
}

func NewUsuarioRepository() *UsuarioRepository {
	return &UsuarioRepository{usuarios: make([]entities.Usuario, 0)}
}

func (r *UsuarioRepository) ListarTodos() []entities.Usuario {
	r.mu.RLock()
	defer r.mu.RUnlock()
	copia := make([]entities.Usuario, len(r.usuarios))
	copy(copia, r.usuarios)
	return copia
}

func (r *UsuarioRepository) Guardar(u entities.Usuario) (entities.Usuario, error) {
	r.mu.Lock()
	defer r.mu.Unlock()
	for _, existente := range r.usuarios {
		if existente.Cedula == u.Cedula {
			return entities.Usuario{}, ErrUsuarioDuplicado
		}
	}
	r.usuarios = append(r.usuarios, u)
	return u, nil
}
