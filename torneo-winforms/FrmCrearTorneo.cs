using System;
using System.Linq;
using System.Windows.Forms;
using TorneoWinForms.Modelos;
using TorneoWinForms.Servicios;

namespace TorneoWinForms
{
    public partial class FrmCrearTorneo : Form
    {
        public Tournament? CreatedTournament { get; private set; }

        public FrmCrearTorneo()
        {
            InitializeComponent();

            cmbCategoria.DataSource = Enum.GetValues(typeof(Categoria));
            cmbCategoria.SelectedItem = Categoria.SinDefinir;
            // === Fondo de cancha ===
            this.BackgroundImage = Properties.Resources.UEFA_Champions_League_Mobile_Background___Foto_di_calcio__Immagini_di_calcio__Sfondi;
            this.BackgroundImageLayout = ImageLayout.Stretch;
            this.DoubleBuffered = true; // suaviza el repintado y quita parpadeos

            // (Opcional) si tienes un panel "tarjeta" central, dale un blanco traslúcido:
            // Si tu panel se llama, por ejemplo, pnlCard, descomenta esta línea:
            // pnlCard.BackColor = Color.FromArgb(230, Color.White);

            // Si NO sabes el nombre del panel, toma el primero que encuentre:
            var panelTarjeta = this.Controls.OfType<Panel>().FirstOrDefault();
            if (panelTarjeta != null)
                panelTarjeta.BackColor = Color.FromArgb(230, Color.White);
        }

public string NombreIngresado =>
    (txtNombre?.Text ?? "").Trim();

    public string CategoriaSeleccionadaTexto =>
        (cmbCategoria?.SelectedItem?.ToString() ?? "SinDefinir").Trim();

    public Categoria CategoriaSeleccionadaEnum
    {
        get
        {
            var txt = CategoriaSeleccionadaTexto;
            return Enum.TryParse<Categoria>(txt, true, out var cat)
                ? cat
                : Categoria.SinDefinir;
        }
    }

    private void btnOk_Click(object? sender, EventArgs e)
        {
            var nombre = txtNombre.Text.Trim();
            if (string.IsNullOrWhiteSpace(nombre))
            {
                MessageBox.Show("Escribe el nombre del torneo.", "Falta nombre",
                    MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }

            var cat = (Categoria)(cmbCategoria.SelectedItem ?? Categoria.SinDefinir);

            var t = new Tournament
            {
                name = nombre,
                category = cat
                // teams y matches ya están inicializados por defecto (get-only)
            };


            TournamentService.AddTournament(t.name, t.category);


            CreatedTournament = t;
            DialogResult = DialogResult.OK;
        }

        private void btnCancel_Click(object? sender, EventArgs e) =>
            DialogResult = DialogResult.Cancel;
    }
}
