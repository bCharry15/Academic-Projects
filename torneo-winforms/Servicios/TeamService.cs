using System.Collections.Generic;
using TorneoWinForms.Modelos;
using MySql.Data.MySqlClient;

namespace TorneoWinForms.Servicios
{
    public static class TeamService
    {
        public static void AddTeam(string teamName, string torneoName)
        {
            using var conn = DB.GetConnection();
            conn.Open();

            using var check = new MySqlCommand(
                "SELECT name FROM equipos WHERE name=@e AND torneo_name=@t", conn);

            check.Parameters.AddWithValue("@e", teamName);
            check.Parameters.AddWithValue("@t", torneoName);

            if (check.ExecuteScalar() != null)
                throw new Exception("Ese equipo ya existe en este torneo.");

            using var cmd = new MySqlCommand(
                @"INSERT INTO equipos 
                  (name, torneo_name, wins, draws, losses, goalsFor, goalsAgainst)
                  VALUES (@e, @t, 0, 0, 0, 0, 0)", conn);

            cmd.Parameters.AddWithValue("@e", teamName);
            cmd.Parameters.AddWithValue("@t", torneoName);

            cmd.ExecuteNonQuery();
        }
        //actualizar estadisticas 
        public static void UpdateTeamStats(Team t, string torneoName)
        {
            MessageBox.Show($"Guardando {t.name} - GF:{t.goalsFor} GA:{t.goalsAgainst} W:{t.wins} D:{t.draws} L:{t.losses}");

            using var conn = DB.GetConnection();
            conn.Open();

            using var cmd = new MySqlCommand(
                @"UPDATE equipos SET
            wins=@w,
            draws=@d,
            losses=@l,
            goalsFor=@gf,
            goalsAgainst=@ga
          WHERE name=@n AND torneo_name=@t", conn);

            cmd.Parameters.AddWithValue("@w", t.wins);
            cmd.Parameters.AddWithValue("@d", t.draws);
            cmd.Parameters.AddWithValue("@l", t.losses);
            cmd.Parameters.AddWithValue("@gf", t.goalsFor);
            cmd.Parameters.AddWithValue("@ga", t.goalsAgainst);
            cmd.Parameters.AddWithValue("@n", t.name);
            cmd.Parameters.AddWithValue("@t", torneoName);

            cmd.ExecuteNonQuery();
        }


        public static List<Team> GetTeamsByTournament(string torneoName)
        {
            var list = new List<Team>();

            using var conn = DB.GetConnection();
            conn.Open();

            using var cmd = new MySqlCommand(
                "SELECT name, wins, draws, losses, goalsFor, goalsAgainst " +
                "FROM equipos WHERE torneo_name = @t",
                conn);

            cmd.Parameters.AddWithValue("@t", torneoName);

            using var reader = cmd.ExecuteReader();
            while (reader.Read())
            {
                list.Add(new Team
                {
                    name = reader.GetString("name"),
                    wins = reader.GetInt32("wins"),
                    draws = reader.GetInt32("draws"),
                    losses = reader.GetInt32("losses"),
                    goalsFor = reader.GetInt32("goalsFor"),
                    goalsAgainst = reader.GetInt32("goalsAgainst")
                });
            }

            return list;
        }



    }
}

