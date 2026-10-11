using System.ComponentModel.DataAnnotations;

namespace backend.Models;

public class Disponibilidad
{
    public int Id { get; set; }

    [Range(1, int.MaxValue)]
    public int MedicoId { get; set; }

    // Lunes = 1, martes = 2, ..., domingo = 7.
    [Range(1, 7)]
    public int DiaSemana { get; set; } = 1;

    [Required]
    [RegularExpression(@"^([01][0-9]|2[0-3]):[0-5][0-9]$")]
    public string HoraInicio { get; set; } = "08:00";

    [Required]
    [RegularExpression(@"^([01][0-9]|2[0-3]):[0-5][0-9]$")]
    public string HoraFin { get; set; } = "12:00";

    [Range(5, 240)]
    public int IntervaloMinutos { get; set; } = 30;

    [Range(1, 52)]
    public int VentanaSemanas { get; set; } = 4;

    public bool Activo { get; set; } = true;
}