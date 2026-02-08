using MySql.Data.MySqlClient;
using TorneoWinForms.Modelos;

namespace TorneoWinForms.Servicios
{
    public static class TournamentService
    {
        
        
            public static void AddTournament(string name, Categoria category)
            {
                using var conn = DB.GetConnection();
                conn.Open();

                using var cmd = new MySqlCommand(
                    "INSERT INTO torneos (name, category) VALUES (@n, @c)", conn);

                cmd.Parameters.AddWithValue("@n", name);
                cmd.Parameters.AddWithValue("@c", category.ToString());

               cmd.ExecuteNonQuery();
            }
        

        // Crear torneo
        public static void CrearTorneo(string name, Categoria category)
        {
            using var conn = DB.GetConnection();
            conn.Open();

            using var check = new MySqlCommand(
                "SELECT name FROM torneos WHERE name=@n", conn);

            check.Parameters.AddWithValue("@n", name);

            if (check.ExecuteScalar() != null)
                throw new Exception("Ya existe un torneo con ese nombre.");

            using var cmd = new MySqlCommand(
                "INSERT INTO torneos (name, category) VALUES (@n, @c)", conn);

            cmd.Parameters.AddWithValue("@n", name);
            cmd.Parameters.AddWithValue("@c", (int)category);

            cmd.ExecuteNonQuery();
        }

        // Cargar torneo (con equipos incluidos)
        public static Tournament LoadTournament(string name)
        {
            using var conn = DB.GetConnection();
            conn.Open();

            // Obtener torneo
            using var cmd = new MySqlCommand(
                "SELECT name, category FROM torneos WHERE name=@n", conn);

            cmd.Parameters.AddWithValue("@n", name);

            using var reader = cmd.ExecuteReader();

            if (!reader.Read())
                throw new Exception("Torneo no encontrado.");

            Tournament t = new Tournament
            {
                name = reader.GetString("name"),
                category = (Categoria)reader.GetInt32("category")
            };

            reader.Close();

            // Cargar equipos asociados
            using var cmd2 = new MySqlCommand(
                "SELECT name, wins, draws, losses, goalsFor, goalsAgainst FROM equipos WHERE torneo_name=@n",
                conn);

            cmd2.Parameters.AddWithValue("@n", name);

            using var r2 = cmd2.ExecuteReader();

            while (r2.Read())
            {
                t.teams.Add(new Team
                {
                    name = r2.GetString("name"),
                    wins = r2.GetInt32("wins"),
                    draws = r2.GetInt32("draws"),
                    losses = r2.GetInt32("losses"),
                    goalsFor = r2.GetInt32("goalsFor"),
                    goalsAgainst = r2.GetInt32("goalsAgainst")
                });
            }

            return t;
        }

        // Obtener lista de torneos
        public static List<string> ObtenerTorneos()
        {
            List<string> lista = new();

            using var conn = DB.GetConnection();
            conn.Open();

            using var cmd = new MySqlCommand("SELECT name FROM torneos", conn);

            using var reader = cmd.ExecuteReader();

            while (reader.Read())
                lista.Add(reader.GetString("name"));

            return lista;
        }
    }
}
