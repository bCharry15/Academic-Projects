using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MySql.Data.MySqlClient;

namespace TorneoWinForms.Servicios
{
    public static class DB
    {
        public static MySqlConnection GetConnection()
        {

            return new MySqlConnection(

                "Server=127.0.0.1;Database=tournament;Uid=root;Pwd=12345678;Port=3307;"
            );
        }
    }
}


