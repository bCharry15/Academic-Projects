using System;
using System.Linq;
using System.Windows.Forms;
using TorneoWinForms.Modelos;
using TorneoWinForms.Servicios;

namespace TorneoWinForms
{
    public partial class FrmWelcome : Form
    {
        public FrmWelcome()
        {
            InitializeComponent();
            // No enganches eventos aquí si ya están en el Designer.
            // Si por alguna razón no están, podrías descomentar:
            // btnAbrirTorneo.Click += btnAbrirTorneo_Click;
            // btnCrearTorneo.Click += btnCrearTorneo_Click;
            // btnSalir.Click       += btnSalir_Click;

            this.BackgroundImage = Properties.Resources.UEFA_Champions_League_Mobile_Background___Foto_di_calcio__Immagini_di_calcio__Sfondi;
            this.BackgroundImageLayout = ImageLayout.Stretch;
            this.DoubleBuffered = true;


        }

        // ==========================
        //  Crear Torneo
        // ==========================
        private void btnCrearTorneo_Click(object sender, EventArgs e)
        {
            using var dlg = new FrmCrearTorneo();
            if (dlg.ShowDialog(this) == DialogResult.OK && dlg.CreatedTournament != null)
            {
                // Guarda en el store si aplica en tu implementación
                // (reemplaza Add por el método que sí tengas disponible)
                try { TournamentService.AddTournament(dlg.CreatedTournament.name,
    dlg.CreatedTournament.category); } catch { /* store opcional */ }

                // Abre el gestor (Form1) con el torneo recién creado
                using var frm = new Form1();
                frm.SetTournament(dlg.CreatedTournament);
                frm.ShowDialog(this);
            }
        }

        // ==========================
        //  Abrir/Entrar a Torneo
        // ==========================
        private void btnAbrirTorneo_Click(object sender, EventArgs e)
        {
            // 1) Trae todos los torneos guardados
            var all = TournamentStore.LoadAll(); // Usa tu servicio/Store actual

            if (all == null || all.Count == 0)
            {
                MessageBox.Show("Aún no hay torneos creados. Usa 'Create Tournament' primero.",
                                "Aviso", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }

            // 2) Abre el selector
            using (var dlg = new FrmSeleccionarTorneo(all))
            {
                if (dlg.ShowDialog(this) != DialogResult.OK || dlg.SelectedTournament == null)
                    return;

                // 3) Abre el gestor (Form1) con el torneo elegido
                using var frm = new Form1();
                frm.SetTournament(dlg.SelectedTournament);
                frm.ShowDialog(this);
            }
        }

        // Alias opcional si el Designer está cableado a otro nombre
        private void btnEnterTournament_Click(object sender, EventArgs e)
        {
            btnAbrirTorneo_Click(sender, e);
        }

        // ==========================
        //  Salir
        // ==========================
        private void btnSalir_Click(object sender, EventArgs e)
        {
            Close();
        }

        private void btnSalir_Click_1(object sender, EventArgs e)
        {

        }
    }
}
