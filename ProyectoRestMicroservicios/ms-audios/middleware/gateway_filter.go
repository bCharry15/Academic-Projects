package middleware

import (
	"crypto/subtle"
	"net/http"

	"github.com/gin-gonic/gin"
)

// HeaderGateway es la cabecera que solo el API Gateway agrega a las peticiones.
const HeaderGateway = "X-Gateway-Token"

// FiltroGateway rechaza toda petición que no traiga el token secreto del API Gateway,
// es decir, cualquier petición que no haya pasado por el gateway.
func FiltroGateway(secreto string) gin.HandlerFunc {
	return func(ctx *gin.Context) {
		token := ctx.GetHeader(HeaderGateway)
		if token == "" || subtle.ConstantTimeCompare([]byte(token), []byte(secreto)) != 1 {
			ctx.AbortWithStatusJSON(http.StatusForbidden, gin.H{
				"error": "acceso denegado: la petición debe pasar por el API Gateway",
			})
			return
		}
		ctx.Next()
	}
}
