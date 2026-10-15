package entities

// Audio es la entidad de dominio que se almacena en el repositorio.
type Audio struct {
	Titulo   string
	Duracion int // en segundos
	Tipo     string
}