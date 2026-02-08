using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Windows.Forms;
using TorneoWinForms.Modelos;
using TorneoWinForms.Servicios;

namespace TorneoWinForms
{
    public partial class Form1 : Form
    {
        private Tournament? _torneo;

        // Útil si otro form necesita leer el torneo activo
        public Tournament? CurrentTournament => _torneo;

        public Form1()
        {
            InitializeComponent();

            // Cableado de eventos (ya existen en el Designer)
            btnCrearTorneo.Click += btnCrearTorneo_Click;
            btnInscribirEquipo.Click += btnInscribirEquipo_Click;
            btnProgramar.Click += btnProgramar_Click;
            btnCargarResultado.Click += btnCargarResultado_Click;
            btnEliminar.Click += btnEliminar_Click;

            SetTournament(null);
            SetTournament(null);

            ApplyStadiumBackground();
        }

        private void ApplyStadiumBackground()
        {
            var img = TorneoWinForms.Properties.Resources.bg_cancha;

            // 1) Si hay algún contenedor Dock=Fill (Panel, TableLayoutPanel, etc.) úsalo como host del fondo.
            Control host = this;
            var dockFill = this.Controls.Cast<Control>()
                             .FirstOrDefault(c => c.Dock == DockStyle.Fill);
            if (dockFill != null) host = dockFill;

            host.BackgroundImage = img;
            host.BackgroundImageLayout = ImageLayout.Stretch;

            // 2) Intenta que los contenedores intermedios “dejen pasar” el fondo
            MakeContainersTransparent(this);
        }

        private void MakeContainersTransparent(Control root)
        {
            foreach (Control c in root.Controls)
            {
                // Panel, GroupBox, TableLayoutPanel, FlowLayoutPanel, etc.
                if (c is Panel || c is GroupBox || c is TableLayoutPanel || c is FlowLayoutPanel)
                {
                    c.BackColor = Color.Transparent;
                }

                // Si tienes TabControl, el fondo conviene ponerlo en cada TabPage también:
                if (c is TabControl tabs)
                {
                    var img = TorneoWinForms.Properties.Resources.fondo;
                    foreach (TabPage page in tabs.TabPages)
                    {
                        page.BackgroundImage = img;
                        page.BackgroundImageLayout = ImageLayout.Stretch;
                    }
                    // El propio TabControl puede ir transparente para que se vea el fondo de atrás
                    tabs.BackColor = Color.Transparent;
                }

                // Recurse
                MakeContainersTransparent(c);
            }
        }



        // --------- Accesos a controles SIN depender del nombre del Designer ----------
        private TabControl Tabs =>
            this.Controls.OfType<TabControl>().FirstOrDefault()
            ?? throw new InvalidOperationException("No se encontró TabControl en el formulario.");

        private TabPage TabEquipos =>
            Tabs.TabPages.Cast<TabPage>()
                .FirstOrDefault(p => p.Text.Trim().Equals("Equipos", StringComparison.OrdinalIgnoreCase))
            ?? Tabs.TabPages[0];

        private TabPage TabPartidos =>
            Tabs.TabPages.Cast<TabPage>()
                .FirstOrDefault(p => p.Text.Trim().Equals("Partidos", StringComparison.OrdinalIgnoreCase))
            ?? Tabs.TabPages[Math.Min(1, Tabs.TabPages.Count - 1)];

        private DataGridView GrdEquipos =>
            TabEquipos.Controls.OfType<DataGridView>().FirstOrDefault()
            ?? throw new InvalidOperationException("No se encontró un DataGridView en la pestaña 'Equipos'.");

        private DataGridView GrdPartidos =>
            TabPartidos.Controls.OfType<DataGridView>().FirstOrDefault()
            ?? throw new InvalidOperationException("No se encontró un DataGridView en la pestaña 'Partidos'.");

        // ----------------------------- API pública -----------------------------
        // Mantén **solo este** SetTournament para evitar la ambigüedad.
        public void SetTournament(Tournament? t)
        {
            _torneo = t;

            lblTitulo.Text = _torneo == null
                ? "Torneo: (ninguno)"
                : $"Torneo: {_torneo.name.ToUpper()}";

            btnCrearTorneo.Visible = _torneo == null;

            // ============================
            //   Cargar equipos desde BD
            // ============================
            if (_torneo != null)
            {
                var equipos = TeamService.GetTeamsByTournament(_torneo.name);

                _torneo.teams.Clear();
                foreach (var eq in equipos)
                    _torneo.teams.Add(eq);
            }

            RefreshEquipos();
            RefreshPartidos();
        }

        // Llamada corta para refrescar todo (útil si otro form modifica el modelo)
        public void RefreshAll()
        {
            RefreshEquipos();
            RefreshPartidos();
        }

        // ----------------------------- Botones -----------------------------
        private void btnCrearTorneo_Click(object? sender, EventArgs e)
        {
            using var frm = new FrmCrearTorneo();
            if (frm.ShowDialog(this) == DialogResult.OK && frm.CreatedTournament != null)
                SetTournament(frm.CreatedTournament);
        }

        private void btnInscribirEquipo_Click(object? sender, EventArgs e)
        {
            if (_torneo == null)
            {
                MessageBox.Show("Primero crea o abre un torneo.", "Aviso",
                    MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }

            using var frm = new FrmInscribirEquipo(_torneo);
            if (frm.ShowDialog(this) == DialogResult.OK)
                RefreshEquipos();
        }

        private void btnProgramar_Click(object? sender, EventArgs e)
        {
            if (_torneo == null)
            {
                MessageBox.Show("Primero crea o abre un torneo.", "Aviso",
                    MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (_torneo.teams.Count < 2)
            {
                MessageBox.Show("Necesitas al menos dos equipos para programar un partido.",
                    "Aviso", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }

            // Este form NO debe pedir marcador (solo local/visita/fecha/estadio)
            using var frm = new FrmProgramarPartido(_torneo);
            if (frm.ShowDialog(this) == DialogResult.OK && frm.CreatedMatch != null)
            {
                _torneo.matches.Add(frm.CreatedMatch);
                RefreshPartidos();
            }
        }

        private void btnCargarResultado_Click(object? sender, EventArgs e)
        {
            if (_torneo == null)
            {
                MessageBox.Show("No hay torneo activo.", "Aviso",
                    MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }

            Tabs.SelectedTab = TabPartidos;

            if (GrdPartidos.Rows.Count == 0)
            {
                MessageBox.Show("No hay partidos para cargar resultado.", "Aviso",
                    MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }

            var row = GrdPartidos.CurrentRow ?? GrdPartidos.Rows[0];
            if (row?.DataBoundItem is not MatchVM vm || vm.MatchRef is not Match match)
            {
                MessageBox.Show("Selecciona un partido en la pestaña 'Partidos' para cargar el resultado.",
                    "Falta selección", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }

            using var frm = new FrmCargarResultado(_torneo, match);
            if (frm.ShowDialog(this) == DialogResult.OK)
            {
                RefreshPartidos();
                RefreshEquipos(); // recalcular tabla en memoria

                // ============================
                //   GUARDAR ESTADÍSTICAS EN BD
                // ============================
                foreach (var team in _torneo.teams)
                    TeamService.UpdateTeamStats(team, _torneo.name);
            }

        }

        private void btnEliminar_Click(object? sender, EventArgs e)
        {
            if (_torneo == null)
            {
                MessageBox.Show("No hay torneo abierto.", "Aviso",
                    MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }

            var ok = MessageBox.Show(
                $"¿Seguro deseas eliminar el torneo \"{_torneo.name}\"?",
                "Confirmación", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);

            if (ok == DialogResult.Yes)
            {
                // TODO: Reemplaza por el método real de tu store (p.ej., Delete/SaveAll/etc.)
                // try { TournamentStore.Remove(_torneo); } catch { }
                SetTournament(null);
            }
        }

        // ----------------------------- Grillas -----------------------------
        private void RefreshEquipos()
        {
            GrdEquipos.Columns.Clear();

            if (_torneo == null || _torneo.teams.Count == 0)
            {
                GrdEquipos.DataSource = null;
                return;
            }

            var standings = ComputeStandings(_torneo);
            GrdEquipos.AutoGenerateColumns = true;
            GrdEquipos.DataSource = new BindingList<StandingVM>(standings);
        }

        private void RefreshPartidos()
        {
            GrdPartidos.Columns.Clear();

            if (_torneo == null || _torneo.matches.Count == 0)
            {
                GrdPartidos.DataSource = null;
                return;
            }

            var data = _torneo.matches.Select(m => new MatchVM
            {
                MatchRef = m,
                Local = m.home?.name ?? "(?)",
                Visitante = m.away?.name ?? "(?)",
                Resultado = (m.result.home.HasValue && m.result.away.HasValue)
                              ? $"{m.result.home}-{m.result.away}"
                              : "",
                Fecha = m.date,
                Estadio = string.IsNullOrWhiteSpace(m.stadium) ? "" : m.stadium
            }).ToList();

            GrdPartidos.AutoGenerateColumns = true;
            GrdPartidos.DataSource = new BindingList<MatchVM>(data);
        }

        // ----------------------------- Tabla de posiciones -----------------------------
        private static List<StandingVM> ComputeStandings(Tournament t)
        {

            // Recalcular estadísticas a partir de los partidos
            foreach (var m in t.matches)
            {
                var (h, a) = m.result;
                if (!h.HasValue || !a.HasValue) continue;

                // HOME
                if (m.home != null)
                {
                    m.home.goalsFor += h.Value;
                    m.home.goalsAgainst += a.Value;

                    if (h > a) m.home.wins++;
                    else if (h == a) m.home.draws++;
                    else m.home.losses++;
                }

                // AWAY
                if (m.away != null)
                {
                    m.away.goalsFor += a.Value;
                    m.away.goalsAgainst += h.Value;

                    if (a > h) m.away.wins++;
                    else if (a == h) m.away.draws++;
                    else m.away.losses++;
                }
            }


            // Ahora generamos la lista VM para mostrar en la grilla
            var standings = t.teams
                .Select(team => new StandingVM
                {
                    Equipo = team.name,
                    PJ = team.games,
                    GF = team.goalsFor,
                    GC = team.goalsAgainst,
                    Pts = team.points
                })
                .OrderByDescending(s => s.Pts)
                .ThenByDescending(s => s.GF - s.GC)
                .ThenByDescending(s => s.GF)
                .ToList();

            // Numerar posiciones
            for (int i = 0; i < standings.Count; i++)
                standings[i].Pos = i + 1;

            return standings;
        }

        // ----------------------------- VMs para grillas -----------------------------
        private sealed class StandingVM
        {
            public int Pos { get; set; }
            public string Equipo { get; set; } = "";
            public int PJ { get; set; }
            public int GF { get; set; }
            public int GC { get; set; }
            public int Pts { get; set; }
        }

        private sealed class MatchVM
        {
            [Browsable(false)]
            public Match? MatchRef { get; set; }

            public string Local { get; set; } = "";
            public string Visitante { get; set; } = "";
            public string Resultado { get; set; } = "";
            public DateTime? Fecha { get; set; }
            public string Estadio { get; set; } = "";
        }

        private void dgvEquipos_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void btnCargarResultado_Click_1(object sender, EventArgs e)
        {

        }

    }
}
