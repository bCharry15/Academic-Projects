using System;
using System.Linq;
using System.Windows.Forms;
using TorneoWinForms.Modelos;

namespace TorneoWinForms
{
    public partial class FrmProgramarPartido : Form
    {
        private readonly Tournament _torneo;
        public Match? CreatedMatch { get; private set; }

        public FrmProgramarPartido(Tournament torneo)
        {
            InitializeComponent();
            _torneo = torneo;

            cboLocal.DisplayMember = nameof(Team.name);
            cboVisita.DisplayMember = nameof(Team.name);

            cboLocal.DataSource = _torneo.teams.ToList();
            cboVisita.DataSource = _torneo.teams.ToList();

            if (cboLocal.Items.Count > 0) cboLocal.SelectedIndex = 0;
            if (cboVisita.Items.Count > 1) cboVisita.SelectedIndex = 1;

            dtpFecha.Value = DateTime.Now;
        }

        private void btnOk_Click(object? sender, EventArgs e)
        {
            var local = cboLocal.SelectedItem as Team;
            var visita = cboVisita.SelectedItem as Team;

            if (local == null || visita == null)
            {
                MessageBox.Show("Selecciona ambos equipos.", "Datos incompletos",
                    MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }
            if (ReferenceEquals(local, visita))
            {
                MessageBox.Show("El local y el visitante deben ser distintos.", "Datos inválidos",
                    MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }

            CreatedMatch = new Match
            {
                home = local,
                away = visita,
                date = dtpFecha.Value,
                stadium = txtEstadio.Text?.Trim() ?? string.Empty
            };

            DialogResult = DialogResult.OK;
        }

        private void btnCancel_Click(object? sender, EventArgs e) =>
            DialogResult = DialogResult.Cancel;
    }
}
