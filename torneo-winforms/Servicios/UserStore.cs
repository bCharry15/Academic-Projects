using System;
using System.Collections.Generic;
using System.IO;
using System.Text.Json;
using TorneoWinForms.Modelos;

namespace TorneoWinForms.Servicios
{
    public static class UserStore
    {
        private static readonly string _folder =
            Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData),
                         "TorneoWinForms");
        private static readonly string _file = Path.Combine(_folder, "users.json");

        private static readonly object _lock = new();
        private static List<User> _cache = null!;

        static UserStore()
        {
            Directory.CreateDirectory(_folder);
            if (!File.Exists(_file))
            {
                File.WriteAllText(_file, "[]");
            }
            Reload();
        }

        private static void Reload()
        {
            lock (_lock)
            {
                var json = File.ReadAllText(_file);
                _cache = JsonSerializer.Deserialize<List<User>>(json,
                    new JsonSerializerOptions { PropertyNameCaseInsensitive = true }) ?? new List<User>();
            }
        }

        private static void Save()
        {
            lock (_lock)
            {
                var json = JsonSerializer.Serialize(_cache, new JsonSerializerOptions { WriteIndented = true });
                File.WriteAllText(_file, json);
            }
        }

        public static IEnumerable<User> GetAll()
        {
            lock (_lock) { return new List<User>(_cache); }
        }

        public static User? GetByEmail(string email)
        {
            lock (_lock) { return _cache.Find(u => u.Email.Equals(email, StringComparison.OrdinalIgnoreCase)); }
        }

        public static void Add(User u)
        {
            lock (_lock)
            {
                if (_cache.Exists(x => x.Email.Equals(u.Email, StringComparison.OrdinalIgnoreCase)))
                    throw new InvalidOperationException("Ya existe una cuenta con ese e-mail.");
                _cache.Add(u);
                Save();
            }
        }
    }
}
