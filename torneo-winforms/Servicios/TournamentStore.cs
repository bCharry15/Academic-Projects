using System.Collections.Generic;
using MySql.Data.MySqlClient;
using TorneoWinForms.Modelos;

namespace TorneoWinForms.Servicios
{
    public static class TournamentStore
    {
        // CARGAR TODOS LOS TORNEOS DESDE LA BD
        public static List<Tournament> LoadAll()
        {
            var list = new List<Tournament>();

            using var conn = DB.GetConnection();
            conn.Open();

            using var cmd = new MySqlCommand("SELECT name, category FROM torneos", conn);
            using var reader = cmd.ExecuteReader();

            while (reader.Read())
            {
                list.Add(new Tournament
                {
                    name = reader.GetString(0),
                    category = Enum.Parse<Categoria>(reader.GetString(1))
                });
            }

            return list;
        }
    }
}
