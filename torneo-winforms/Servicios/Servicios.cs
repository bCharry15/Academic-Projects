using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


using MySql.Data.MySqlClient;

namespace TorneoWinForms.
{
    public static class DB
    {
        public static MySqlConnection GetConnection()
        {
            return new MySqlConnection(
                "Server=localhost;Database=torneo;Uid=root;Pwd=12345678;SslMode=none;"
            );
        }
    }
}
