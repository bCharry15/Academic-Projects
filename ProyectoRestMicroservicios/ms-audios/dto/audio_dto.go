package dto

// AudioDTO es el objeto que viaja por la red en formato JSON.
type AudioDTO struct {
	Titulo   string `json:"titulo" binding:"required"`
	Duracion int    `json:"duracion" binding:"required,gt=0"`
	Tipo     string `json:"tipo" binding:"required"`
}
