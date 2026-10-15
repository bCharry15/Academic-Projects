package controllers

import (
	"errors"
	"net/http"

	"github.com/gin-gonic/gin"

	"ms-audios/dto"
	"ms-audios/repositories"
	"ms-audios/services"
)

type AudioController struct {
	service *services.AudioService
}

func NewAudioController(service *services.AudioService) *AudioController {
	return &AudioController{service: service}
}

func (c *AudioController) RegistrarRutas(r gin.IRouter) {
	r.GET("/audios", c.Listar)
	r.POST("/audios", c.Registrar)
}

// GET /audios
func (c *AudioController) Listar(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, c.service.Listar())
}

// POST /audios
func (c *AudioController) Registrar(ctx *gin.Context) {
	var body dto.AudioDTO

	if err := ctx.ShouldBindJSON(&body); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{
			"error": "datos inválidos: " + err.Error(),
		})
		return
	}

	creado, err := c.service.Registrar(body)

	if errors.Is(err, repositories.ErrAudioDuplicado) {
		ctx.JSON(http.StatusConflict, gin.H{
			"error": err.Error(),
		})
		return
	}

	if err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{
			"error": err.Error(),
		})
		return
	}

	ctx.JSON(http.StatusCreated, creado)
}
