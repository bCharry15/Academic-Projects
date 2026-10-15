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

	"ms-productos/controllers"
	"ms-productos/discovery"
	"ms-productos/middleware"
	"ms-productos/repositories"
	"ms-productos/services"
)

const nombreServicio = "ms-productos"

func main() {
	puerto, _ := strconv.Atoi(env("PORT", "8081"))
	host := env("SERVICE_HOST", "127.0.0.1")
	consulAddr := env("CONSUL_ADDR", "127.0.0.1:8500")
	secreto := env("GATEWAY_SECRET", "secreto-gateway-2026")

	// Capas: acceso a datos -> fachada (services) -> controladores
	repo := repositories.NewProductoRepository()
	service := services.NewProductoService(repo)
	controller := controllers.NewProductoController(service)

	router := gin.Default()
	router.Use(middleware.FiltroGateway(secreto))
	controller.RegistrarRutas(router)

	srv := &http.Server{Addr: fmt.Sprintf(":%d", puerto), Handler: router}
	go func() {
		log.Printf("%s escuchando en :%d", nombreServicio, puerto)
		if err := srv.ListenAndServe(); err != nil && !errors.Is(err, http.ErrServerClosed) {
			log.Fatalf("error en el servidor: %v", err)
		}
	}()

	// Al lanzarse, el microservicio se registra en el directorio de descubrimiento.
	registro, err := discovery.Registrar(consulAddr, nombreServicio, host, puerto)
	if err != nil {
		log.Fatalf("no fue posible registrarse en Consul: %v", err)
	}

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
