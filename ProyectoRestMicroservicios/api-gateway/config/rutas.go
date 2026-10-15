package config

// Ruta asocia un prefijo público del gateway con el nombre de un servicio en Consul.
type Ruta struct {
	Prefijo  string // ruta pública en el gateway, p. ej. /api/productos
	Servicio string // nombre con el que el microservicio se registró en Consul
	Destino  string // ruta interna en el microservicio, p. ej. /productos
}

var Rutas = []Ruta{
	{Prefijo: "/api/productos", Servicio: "ms-productos", Destino: "/productos"},
	{Prefijo: "/api/usuarios", Servicio: "ms-usuarios", Destino: "/usuarios"},
	{Prefijo: "/api/audios", Servicio: "ms-audios", Destino: "/audios"},
}
