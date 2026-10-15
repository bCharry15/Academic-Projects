package controllers

import (
	"errors"
	"net/http"

	"github.com/gin-gonic/gin"

	"ms-productos/dto"
	"ms-productos/repositories"
	"ms-productos/services"
)

type ProductoController struct {
	service *services.ProductoService
}

func NewProductoController(service *services.ProductoService) *ProductoController {
	return &ProductoController{service: service}
}

func (c *ProductoController) RegistrarRutas(r gin.IRouter) {
	r.GET("/productos", c.Listar)
	r.POST("/productos", c.Registrar)
}

// GET /productos
func (c *ProductoController) Listar(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, c.service.Listar())
}

// POST /productos
func (c *ProductoController) Registrar(ctx *gin.Context) {
	var body dto.ProductoDTO
	if err := ctx.ShouldBindJSON(&body); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"error": "datos inválidos: " + err.Error()})
		return
	}
	creado, err := c.service.Registrar(body)
	if errors.Is(err, repositories.ErrProductoDuplicado) {
		ctx.JSON(http.StatusConflict, gin.H{"error": err.Error()})
		return
	}
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	ctx.JSON(http.StatusCreated, creado)
}
