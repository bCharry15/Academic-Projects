using backend.Data;
using backend.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace backend.Controllers;

[ApiController]
[Route("api/medicos")]
public class MedicosController : ControllerBase
{
    private readonly AppDbContext _db;

    public MedicosController(AppDbContext db)
    {
        _db = db;
    }

    // GET: api/medicos
    [HttpGet]
    public async Task<ActionResult<List<Medico>>> Listar()
    {
        return await _db.Medicos
            .AsNoTracking()
            .OrderBy(m => m.Id)
            .ToListAsync();
    }

    // GET: api/medicos/1
    [HttpGet("{id:int}")]
    public async Task<ActionResult<Medico>> Obtener(int id)
    {
        var medico = await _db.Medicos.FindAsync(id);

        if (medico is null)
        {
            return NotFound();
        }

        return medico;
    }

    // POST: api/medicos
    [HttpPost]
    public async Task<ActionResult<Medico>> Crear(Medico entrada)
    {
        var medico = new Medico
        {
            NombreCompleto = entrada.NombreCompleto.Trim(),
            Especialidad = entrada.Especialidad.Trim(),
            IntervaloMinutos = entrada.IntervaloMinutos,
            Activo = entrada.Activo
        };

        _db.Medicos.Add(medico);
        await _db.SaveChangesAsync();

        return CreatedAtAction(
            nameof(Obtener),
            new { id = medico.Id },
            medico
        );
    }

    // PUT: api/medicos/1
    [HttpPut("{id:int}")]
    public async Task<IActionResult> Actualizar(int id, Medico entrada)
    {
        var medico = await _db.Medicos.FindAsync(id);

        if (medico is null)
        {
            return NotFound();
        }

        medico.NombreCompleto = entrada.NombreCompleto.Trim();
        medico.Especialidad = entrada.Especialidad.Trim();
        medico.IntervaloMinutos = entrada.IntervaloMinutos;
        medico.Activo = entrada.Activo;

        await _db.SaveChangesAsync();

        return NoContent();
    }

    // DELETE: api/medicos/1
    [HttpDelete("{id:int}")]
    public async Task<IActionResult> Eliminar(int id)
    {
        var medico = await _db.Medicos.FindAsync(id);

        if (medico is null)
        {
            return NotFound();
        }

        bool tieneHorarios = await _db.Disponibilidades
            .AnyAsync(d => d.MedicoId == id);

        if (tieneHorarios)
        {
            return Conflict(new
            {
                mensaje = "El médico tiene horarios registrados. " +
                          "Elimínalos antes de borrar al médico."
            });
        }

        _db.Medicos.Remove(medico);
        await _db.SaveChangesAsync();

        return NoContent();
    }
}