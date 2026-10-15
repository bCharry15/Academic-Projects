package dto

// UsuarioDTO es el objeto que viaja por la red (JSON) entre cliente y controlador.
type UsuarioDTO struct {
	Cedula    string `json:"cedula" binding:"required"`
	Nombres   string `json:"nombres" binding:"required"`
	Apellidos string `json:"apellidos" binding:"required"`
}
