package entities

// Producto es la entidad de dominio que se almacena en el repositorio.
type Producto struct {
	Codigo string
	Nombre string
	Precio float64
	Stock  int
}
