using System.ComponentModel.DataAnnotations;

namespace backend.Models;

public class Medico
{
    public int Id { get; set; }

    [Required]
    [StringLength(120)]
    public string NombreCompleto { get; set; } = "";

    [Required]
    [StringLength(100)]
    public string Especialidad { get; set; } = "";

    [Range(5, 240)]
    public int IntervaloMinutos { get; set; } = 30;

    public bool Activo { get; set; } = true;
}