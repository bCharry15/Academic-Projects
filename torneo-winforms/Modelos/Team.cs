namespace TorneoWinForms.Modelos
{
    public sealed class Team
    {
        public string name { get; set; } = string.Empty;

        // Estadísticas
        public int wins { get; set; }
        public int draws { get; set; }
        public int losses { get; set; }
        public int goalsFor { get; set; }
        public int goalsAgainst { get; set; }

        public int points => wins * 3 + draws;
        public int games => wins + draws + losses;
        public int goalDifference => goalsFor - goalsAgainst;

        public override string ToString() => name;
    }
}

