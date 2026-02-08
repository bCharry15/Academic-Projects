using System;
using System.Windows.Forms;
using TorneoWinForms.Modelos;

namespace TorneoWinForms
{
    public partial class FrmCargarResultado : Form
    {
        private readonly Tournament _torneo;
        private readonly Match _match;

        public FrmCargarResultado(Tournament torneo, Match match)
        {
            InitializeComponent();
            _torneo = torneo;
            _match = match;

            lblLocal.Text = _match.home?.name ?? "(?)";
            lblVisita.Text = _match.away?.name ?? "(?)";

            var (h, a) = _match.result;
            nudHome.Value = h ?? 0;
            nudAway.Value = a ?? 0;
        }

        private void btnOk_Click(object? sender, EventArgs e)
        {
            _match.result = ((int)nudHome.Value, (int)nudAway.Value);
            DialogResult = DialogResult.OK;
        }

        private void btnCancel_Click(object? sender, EventArgs e) =>
            DialogResult = DialogResult.Cancel;
    }
}
