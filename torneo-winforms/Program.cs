using System;
using System.Windows.Forms;

namespace TorneoWinForms
{
    public static class DB
    {
        public static string ConnectionString = "Server=localhost;Database=torneo;Uid=root;Pwd=12345678;";
    }

    internal static class Program
    {
        [STAThread]
        static void Main()
        {
            ApplicationConfiguration.Initialize();

            using var auth = new FrmAuth();
            var r = auth.ShowDialog();

            if (r == DialogResult.OK && auth.LoggedInUser != null)
            {
                // Aquí puedes pasar el usuario a FrmWelcome si quieres saludarlo.
                Application.Run(new FrmWelcome());
            }
            else
            {
                // Canceló el login
                return;
            }
        }
    }
}
