package main

import (
	"context"
	"errors"
	"fmt"
	"log"
	"net/http"
	"os"
	"os/signal"
	"strconv"
	"syscall"
	"time"

	"github.com/gin-gonic/gin"

	"ms-audios/controllers"
	"ms-audios/discovery"
	"ms-audios/middleware"
	"ms-audios/repositories"
	"ms-audios/services"
)

const nombreServicio = "ms-audios"

func main() {
	puerto, _ := strconv.Atoi(env("PORT", "8083"))
	host := env("SERVICE_HOST", "127.0.0.1")
	consulAddr := env("CONSUL_ADDR", "127.0.0.1:8500")
	secreto := env("GATEWAY_SECRET", "secreto-gateway-2026")

	// Conectar las capas.
	repo := repositories.NewAudioRepository()
	service := services.NewAudioService(repo)
	controller := controllers.NewAudioController(service)

	// Aplicar el filtro antes de registrar las rutas.
	router := gin.Default()
	router.Use(middleware.FiltroGateway(secreto))
	controller.RegistrarRutas(router)

	srv := &http.Server{
		Addr:    fmt.Sprintf(":%d", puerto),
		Handler: router,
	}

	go func() {
		log.Printf("%s escuchando en :%d", nombreServicio, puerto)

		if err := srv.ListenAndServe(); err != nil &&
			!errors.Is(err, http.ErrServerClosed) {
			log.Fatalf("error en el servidor: %v", err)
		}
	}()

	// Registrar el microservicio en Consul.
	registro, err := discovery.Registrar(
		consulAddr,
		nombreServicio,
		host,
		puerto,
	)
	if err != nil {
		log.Fatalf("no fue posible registrarse en Consul: %v", err)
	}

	// Esperar la señal de cierre.
	stop := make(chan os.Signal, 1)
	signal.Notify(stop, os.Interrupt, syscall.SIGTERM)
	<-stop

	registro.Desregistrar()

	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()

	_ = srv.Shutdown(ctx)
}

func env(clave, porDefecto string) string {
	if v := os.Getenv(clave); v != "" {
		return v
	}
	return porDefecto
}
