package discovery

import (
	"fmt"
	"net/url"
	"sync/atomic"

	consul "github.com/hashicorp/consul/api"
)

// Resolver consulta el directorio Consul para obtener la dirección de un servicio.
type Resolver struct {
	cliente *consul.Client
	turno   atomic.Uint64
}

func NewResolver(consulAddr string) (*Resolver, error) {
	cfg := consul.DefaultConfig()
	cfg.Address = consulAddr
	cliente, err := consul.NewClient(cfg)
	if err != nil {
		return nil, err
	}
	return &Resolver{cliente: cliente}, nil
}

// Resolver devuelve la URL base de una instancia sana del servicio (round-robin).
func (r *Resolver) Resolver(servicio string) (*url.URL, error) {
	instancias, _, err := r.cliente.Health().Service(servicio, "", true, nil)
	if err != nil {
		return nil, fmt.Errorf("error consultando Consul: %w", err)
	}
	if len(instancias) == 0 {
		return nil, fmt.Errorf("no hay instancias disponibles de %q", servicio)
	}
	i := instancias[int(r.turno.Add(1)%uint64(len(instancias)))]
	host := i.Service.Address
	if host == "" {
		host = i.Node.Address
	}
	return url.Parse(fmt.Sprintf("http://%s:%d", host, i.Service.Port))
}
