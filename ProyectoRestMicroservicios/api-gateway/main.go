package main

import (
	"log"
	"net/http"
	"os"

	"github.com/gin-gonic/gin"

	"api-gateway/config"
	"api-gateway/discovery"
	"api-gateway/proxy"
)

func main() {
	puerto := env("PORT", "8080")
	consulAddr := env("CONSUL_ADDR", "127.0.0.1:8500")
	secreto := env("GATEWAY_SECRET", "secreto-gateway-2026")

	resolver, err := discovery.NewResolver(consulAddr)
	if err != nil {
		log.Fatalf("no fue posible crear el cliente de Consul: %v", err)
	}

	router := gin.Default()
	router.GET("/health", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{"estado": "UP"})
	})
	for _, ruta := range config.Rutas {
		h := proxy.Handler(ruta, resolver, secreto)
		router.Any(ruta.Prefijo, h)
		router.Any(ruta.Prefijo+"/*resto", h)
	}

	log.Printf("API Gateway escuchando en :%s (Consul en %s)", puerto, consulAddr)
	if err := router.Run(":" + puerto); err != nil {
		log.Fatal(err)
	}
}

func env(clave, porDefecto string) string {
	if v := os.Getenv(clave); v != "" {
		return v
	}
	return porDefecto
}
