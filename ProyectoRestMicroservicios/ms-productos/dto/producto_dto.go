package dto

// ProductoDTO es el objeto que viaja por la red (JSON) entre cliente y controlador.
type ProductoDTO struct {
	Codigo string  `json:"codigo" binding:"required"`
	Nombre string  `json:"nombre" binding:"required"`
	Precio float64 `json:"precio" binding:"required,gt=0"`
	Stock  int     `json:"stock" binding:"gte=0"`
}
