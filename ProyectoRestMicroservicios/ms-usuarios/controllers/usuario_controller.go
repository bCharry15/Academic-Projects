package controllers

import (
	"errors"
	"net/http"

	"github.com/gin-gonic/gin"

	"ms-usuarios/dto"
	"ms-usuarios/repositories"
	"ms-usuarios/services"
)

type UsuarioController struct {
	service *services.UsuarioService
}

func NewUsuarioController(service *services.UsuarioService) *UsuarioController {
	return &UsuarioController{service: service}
}

func (c *UsuarioController) RegistrarRutas(r gin.IRouter) {
	r.GET("/usuarios", c.Listar)
	r.POST("/usuarios", c.Registrar)
}

// GET /usuarios
func (c *UsuarioController) Listar(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, c.service.Listar())
}

// POST /usuarios
func (c *UsuarioController) Registrar(ctx *gin.Context) {
	var body dto.UsuarioDTO
	if err := ctx.ShouldBindJSON(&body); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"error": "datos inválidos: " + err.Error()})
		return
	}
	creado, err := c.service.Registrar(body)
	if errors.Is(err, repositories.ErrUsuarioDuplicado) {
		ctx.JSON(http.StatusConflict, gin.H{"error": err.Error()})
		return
	}
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	ctx.JSON(http.StatusCreated, creado)
}
