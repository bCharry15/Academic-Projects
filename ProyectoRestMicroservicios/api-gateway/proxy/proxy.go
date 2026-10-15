package proxy

import (
	"log"
	"net/http"
	"net/http/httputil"
	"strings"

	"github.com/gin-gonic/gin"

	"api-gateway/config"
	"api-gateway/discovery"
)

// HeaderGateway es la cabecera que los microservicios exigen en su filtro.
const HeaderGateway = "X-Gateway-Token"

// Handler crea un manejador Gin que reenvía la petición al microservicio
// correspondiente usando httputil.ReverseProxy.
func Handler(ruta config.Ruta, resolver *discovery.Resolver, secreto string) gin.HandlerFunc {
	return func(c *gin.Context) {
		destino, err := resolver.Resolver(ruta.Servicio)
		if err != nil {
			log.Printf("[gateway] %v", err)
			c.JSON(http.StatusServiceUnavailable, gin.H{"error": err.Error()})
			return
		}

		rp := &httputil.ReverseProxy{
			Rewrite: func(pr *httputil.ProxyRequest) {
				pr.SetURL(destino)
				// /api/productos/xyz -> /productos/xyz
				pr.Out.URL.Path = ruta.Destino + strings.TrimPrefix(pr.In.URL.Path, ruta.Prefijo)
				pr.Out.URL.RawPath = ""
				pr.SetXForwarded()
				// Marca la petición como proveniente del gateway (sobrescribe cualquier valor del cliente).
				pr.Out.Header.Set(HeaderGateway, secreto)
			},
			ErrorHandler: func(w http.ResponseWriter, r *http.Request, err error) {
				log.Printf("[gateway] error hacia %s: %v", ruta.Servicio, err)
				w.Header().Set("Content-Type", "application/json")
				w.WriteHeader(http.StatusBadGateway)
				_, _ = w.Write([]byte(`{"error":"el microservicio no respondió"}`))
			},
		}

		log.Printf("[gateway] %s %s -> %s (%s)", c.Request.Method, c.Request.URL.Path, destino, ruta.Servicio)
		rp.ServeHTTP(c.Writer, c.Request)
	}
}
