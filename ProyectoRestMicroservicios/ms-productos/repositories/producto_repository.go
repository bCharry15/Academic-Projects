package repositories

import (
	"errors"
	"sync"

	"ms-productos/entities"
)

var ErrProductoDuplicado = errors.New("ya existe un producto con ese código")

// ProductoRepository almacena los productos en RAM mediante una lista (slice).
type ProductoRepository struct {
	mu        sync.RWMutex
	productos []entities.Producto
}

func NewProductoRepository() *ProductoRepository {
	return &ProductoRepository{productos: make([]entities.Producto, 0)}
}

func (r *ProductoRepository) ListarTodos() []entities.Producto {
	r.mu.RLock()
	defer r.mu.RUnlock()
	copia := make([]entities.Producto, len(r.productos))
	copy(copia, r.productos)
	return copia
}

func (r *ProductoRepository) Guardar(p entities.Producto) (entities.Producto, error) {
	r.mu.Lock()
	defer r.mu.Unlock()
	for _, existente := range r.productos {
		if existente.Codigo == p.Codigo {
			return entities.Producto{}, ErrProductoDuplicado
		}
	}
	r.productos = append(r.productos, p)
	return p, nil
}
