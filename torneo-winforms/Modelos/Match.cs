using System;

namespace TorneoWinForms.Modelos
{
    public class Match
    {
        public Team home { get; set; } = new();
        public Team away { get; set; } = new();

        // Resultado se carga luego: (golesLocal, golesVisita)
        public (int? home, int? away) result { get; set; } = (null, null);

        // Campos que necesita “Programar Partido”
        public DateTime? date { get; set; }
        public string stadium { get; set; } = string.Empty;

        public Match() { }

        public Match(Team home, Team away)
        {
            this.home = home;
            this.away = away;
        }
    }
}
