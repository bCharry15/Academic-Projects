using System;
using System.Linq;
using System.Windows.Forms;
using TorneoWinForms.Modelos;
using TorneoWinForms.Servicios;   // <-- IMPORTANTE

namespace TorneoWinForms
{
    public partial class FrmInscribirEquipo : Form
    {
        private readonly Tournament _torneo;

        public FrmInscribirEquipo(Tournament torneo)
        {
            _torneo = torneo ?? throw new ArgumentNullException(nameof(torneo));
            InitializeComponent();
        }

        private void btnCrear_Click(object? sender, EventArgs e)
        {
            var nombre = txtNombre.Text.Trim();

            if (string.IsNullOrWhiteSpace(nombre))
            {
                MessageBox.Show("Ingresa el nombre del equipo.", "Aviso",
                    MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtNombre.Focus();
                return;
            }

            // Validación local para evitar duplicados en memoria
            if (_torneo.teams.Any(t =>
                string.Equals(t.name, nombre, StringComparison.OrdinalIgnoreCase)))
            {
                MessageBox.Show("Ya existe un equipo con ese nombre en el torneo.", "Aviso",
                    MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtNombre.SelectAll();
                txtNombre.Focus();
                return;
            }

            try
            {
                // 1. Guardar en BD ***REAL***
                TeamService.AddTeam(nombre, _torneo.name);

                // 2. Agregar también a la lista en memoria (para la UI)
                var nuevo = new Team
                {
                    name = nombre
                };

                _torneo.teams.Add(nuevo);

                MessageBox.Show("Equipo registrado correctamente.", "Éxito",
                    MessageBoxButtons.OK, MessageBoxIcon.Information);

                DialogResult = DialogResult.OK;
                Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show($"No fue posible inscribir el equipo: {ex.Message}", "Error",
                    MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void btnCancelar_Click(object? sender, EventArgs e)
        {
            DialogResult = DialogResult.Cancel;
            Close();
        }
    }
}
