using System.Globalization;
using backend.Data;
using backend.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace backend.Controllers;

[ApiController]
[Route("api/disponibilidades")]
public class DisponibilidadesController : ControllerBase
{
    private readonly AppDbContext _db;

    public DisponibilidadesController(AppDbContext db)
    {
        _db = db;
    }

    // GET: api/disponibilidades
    [HttpGet]
    public async Task<ActionResult<List<Disponibilidad>>> Listar()
    {
        return await _db.Disponibilidades
            .AsNoTracking()
            .OrderBy(d => d.Id)
            .ToListAsync();
    }

    // GET: api/disponibilidades/1
    [HttpGet("{id:int}")]
    public async Task<ActionResult<Disponibilidad>> Obtener(int id)
    {
        var disponibilidad = await _db.Disponibilidades.FindAsync(id);

        if (disponibilidad is null)
        {
            return NotFound();
        }

        return disponibilidad;
    }

    // POST: api/disponibilidades
    [HttpPost]
    public async Task<ActionResult<Disponibilidad>> Crear(
        Disponibilidad entrada)
    {
        var error = await Validar(entrada);

        if (error is not null)
        {
            return BadRequest(new { mensaje = error });
        }

        var disponibilidad = new Disponibilidad
        {
            MedicoId = entrada.MedicoId,
            DiaSemana = entrada.DiaSemana,
            HoraInicio = entrada.HoraInicio,
            HoraFin = entrada.HoraFin,
            IntervaloMinutos = entrada.IntervaloMinutos,
            VentanaSemanas = entrada.VentanaSemanas,
            Activo = entrada.Activo
        };

        _db.Disponibilidades.Add(disponibilidad);
        await _db.SaveChangesAsync();

        return CreatedAtAction(
            nameof(Obtener),
            new { id = disponibilidad.Id },
            disponibilidad
        );
    }

    // PUT: api/disponibilidades/1
    [HttpPut("{id:int}")]
    public async Task<IActionResult> Actualizar(
        int id,
        Disponibilidad entrada)
    {
        var disponibilidad = await _db.Disponibilidades.FindAsync(id);

        if (disponibilidad is null)
        {
            return NotFound();
        }

        var error = await Validar(entrada, id);

        if (error is not null)
        {
            return BadRequest(new { mensaje = error });
        }

        disponibilidad.MedicoId = entrada.MedicoId;
        disponibilidad.DiaSemana = entrada.DiaSemana;
        disponibilidad.HoraInicio = entrada.HoraInicio;
        disponibilidad.HoraFin = entrada.HoraFin;
        disponibilidad.IntervaloMinutos = entrada.IntervaloMinutos;
        disponibilidad.VentanaSemanas = entrada.VentanaSemanas;
        disponibilidad.Activo = entrada.Activo;

        await _db.SaveChangesAsync();

        return NoContent();
    }

    // DELETE: api/disponibilidades/1
    [HttpDelete("{id:int}")]
    public async Task<IActionResult> Eliminar(int id)
    {
        var disponibilidad = await _db.Disponibilidades.FindAsync(id);

        if (disponibilidad is null)
        {
            return NotFound();
        }

        _db.Disponibilidades.Remove(disponibilidad);
        await _db.SaveChangesAsync();

        return NoContent();
    }

    // Se utiliza tanto al crear como al actualizar.
    private async Task<string?> Validar(
        Disponibilidad entrada,
        int? excluirId = null)
    {
        var medico = await _db.Medicos.FindAsync(entrada.MedicoId);

        if (medico is null)
        {
            return "El médico seleccionado no existe.";
        }

        if (entrada.Activo && !medico.Activo)
        {
            return "No puedes activar un horario para un médico inactivo.";
        }

        bool inicioValido = TimeOnly.TryParseExact(
            entrada.HoraInicio,
            "HH:mm",
            CultureInfo.InvariantCulture,
            DateTimeStyles.None,
            out var inicio
        );

        bool finValido = TimeOnly.TryParseExact(
            entrada.HoraFin,
            "HH:mm",
            CultureInfo.InvariantCulture,
            DateTimeStyles.None,
            out var fin
        );

        if (!inicioValido || !finValido)
        {
            return "Las horas deben tener el formato HH:mm.";
        }

        if (fin <= inicio)
        {
            return "La hora final debe ser posterior a la inicial.";
        }

        double duracion = (
            fin.ToTimeSpan() - inicio.ToTimeSpan()
        ).TotalMinutes;

        if (duracion < entrada.IntervaloMinutos)
        {
            return "El horario debe permitir al menos un intervalo de atención.";
        }

        if (entrada.Activo)
        {
            var horarios = await _db.Disponibilidades
                .AsNoTracking()
                .Where(d =>
                    d.MedicoId == entrada.MedicoId &&
                    d.DiaSemana == entrada.DiaSemana &&
                    d.Activo)
                .ToListAsync();

            foreach (var horario in horarios)
            {
                // Al editar, no comparamos el horario consigo mismo.
                if (horario.Id == excluirId)
                {
                    continue;
                }

                var otroInicio = TimeOnly.ParseExact(
                    horario.HoraInicio,
                    "HH:mm",
                    CultureInfo.InvariantCulture
                );

                var otroFin = TimeOnly.ParseExact(
                    horario.HoraFin,
                    "HH:mm",
                    CultureInfo.InvariantCulture
                );

                if (inicio < otroFin && fin > otroInicio)
                {
                    return "El horario se cruza con otra disponibilidad activa del médico.";
                }
            }
        }

        return null;
    }
}