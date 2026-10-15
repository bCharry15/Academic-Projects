package discovery

import (
	"fmt"
	"log"
	"time"

	consul "github.com/hashicorp/consul/api"
)

// Registro mantiene el registro del microservicio en Consul.
type Registro struct {
	cliente *consul.Client
	id      string
	checkID string
	parar   chan struct{}
}

// Registrar inscribe el microservicio en el directorio Consul con un health check TTL
// que el propio servicio renueva periódicamente.
func Registrar(consulAddr, nombre, host string, puerto int) (*Registro, error) {
	cfg := consul.DefaultConfig()
	cfg.Address = consulAddr
	cliente, err := consul.NewClient(cfg)
	if err != nil {
		return nil, err
	}

	id := fmt.Sprintf("%s-%s-%d", nombre, host, puerto)
	checkID := "check-" + id
	reg := &consul.AgentServiceRegistration{
		ID:      id,
		Name:    nombre,
		Address: host,
		Port:    puerto,
		Tags:    []string{"gin", "rest"},
		Check: &consul.AgentServiceCheck{
			CheckID:                        checkID,
			TTL:                            "15s",
			DeregisterCriticalServiceAfter: "1m",
		},
	}
	if err := cliente.Agent().ServiceRegister(reg); err != nil {
		return nil, err
	}

	r := &Registro{cliente: cliente, id: id, checkID: checkID, parar: make(chan struct{})}
	go r.latido()
	log.Printf("servicio %q registrado en Consul (%s) como %s", nombre, consulAddr, id)
	return r, nil
}

func (r *Registro) latido() {
	t := time.NewTicker(5 * time.Second)
	defer t.Stop()
	for {
		if err := r.cliente.Agent().UpdateTTL(r.checkID, "ok", consul.HealthPassing); err != nil {
			log.Printf("error renovando TTL en Consul: %v", err)
		}
		select {
		case <-t.C:
		case <-r.parar:
			return
		}
	}
}

// Desregistrar elimina el servicio de Consul (al apagarse).
func (r *Registro) Desregistrar() {
	close(r.parar)
	if err := r.cliente.Agent().ServiceDeregister(r.id); err != nil {
		log.Printf("error desregistrando de Consul: %v", err)
		return
	}
	log.Printf("servicio %s desregistrado de Consul", r.id)
}
