using MySql.Data.MySqlClient;
using TorneoWinForms.Modelos;

namespace TorneoWinForms.Servicios
{
    public static class AuthService
    {
        // =======================================
        // REGISTRAR USUARIO
        // =======================================
        public static User Register(string nombre, string email, string password)
        {
            // 1. Validación básica
            if (string.IsNullOrWhiteSpace(nombre) ||
                string.IsNullOrWhiteSpace(email) ||
                string.IsNullOrWhiteSpace(password))
                throw new Exception("Todos los campos son obligatorios.");

            using var conn = DB.GetConnection();
            conn.Open();

            // 2. Verificar si el email ya está registrado
            using (var checkCmd = new MySqlCommand(
                "SELECT id FROM usuarios WHERE email = @em", conn))
            {
                checkCmd.Parameters.AddWithValue("@em", email);

                var existe = checkCmd.ExecuteScalar();
                if (existe != null)
                    throw new Exception("El correo ya está registrado.");
            }

            // 3. Crear usuario nuevo
            using (var cmd = new MySqlCommand(
                "INSERT INTO usuarios (nombre, email, password) VALUES (@n, @e, @p)", conn))
            {
                cmd.Parameters.AddWithValue("@n", nombre);
                cmd.Parameters.AddWithValue("@e", email);
                cmd.Parameters.AddWithValue("@p", password);

                cmd.ExecuteNonQuery();
            }

            return new User { Name = nombre, Email = email,PasswordHash = password };
        }



        // =======================================
        // LOGIN
        // =======================================
        public static User Login(string email, string password)
        {
            using var conn = DB.GetConnection();
            conn.Open();

            using var cmd = new MySqlCommand(
                "SELECT id, nombre, email, password FROM usuarios WHERE email=@e", conn);
            cmd.Parameters.AddWithValue("@e", email);

            using var reader = cmd.ExecuteReader();
            if (!reader.Read())
                throw new Exception("Usuario no encontrado.");

            string passDb = reader.GetString("password");

            if (passDb != password)
                throw new Exception("Contraseña incorrecta.");

            return new User
            {
               
                Name = reader.GetString("nombre"),
                Email = reader.GetString("email")
            };
        }
    }
}
