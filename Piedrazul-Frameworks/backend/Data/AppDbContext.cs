using backend.Models;
using Microsoft.EntityFrameworkCore;

namespace backend.Data;

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options)
        : base(options)
    {
    }

    public DbSet<Medico> Medicos => Set<Medico>();

    public DbSet<Disponibilidad> Disponibilidades =>
        Set<Disponibilidad>();

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Disponibilidad>()
            .HasOne<Medico>()
            .WithMany()
            .HasForeignKey(d => d.MedicoId)
            .OnDelete(DeleteBehavior.Restrict);
    }
}