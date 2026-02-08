using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Windows.Forms;
using TorneoWinForms.Modelos;

namespace TorneoWinForms
{
    public partial class FrmSeleccionarTorneo : Form
    {
        // ===== Item visual para la lista =====
        private sealed class DisplayItem
        {
            public string Text { get; set; } = "";
            public Tournament Value { get; set; } = default!;
            public override string ToString() => Text;
        }

        // ===== Campos =====
        private readonly List<Tournament> _torneos;
        private ListBox? _lst;               // ListBox real, cualquiera que tengas en el Designer
        private Button? _btnOk;              // Botón Abrir (nombre o texto variable)
        private Button? _btnCancelar;        // Botón Cancelar (nombre o texto variable)

        public Tournament? SelectedTournament { get; private set; }

        public FrmSeleccionarTorneo(IEnumerable<Tournament> torneos)
        {
            InitializeComponent();

            _torneos = torneos?.ToList() ?? new List<Tournament>();

            // Busca controles por nombre o por texto (no dependemos del Designer)
            _lst = FindListBox(new[] { "lstTorneos", "listBoxTorneos", "lst", "listBox1" });
            _btnOk = FindButton(new[] { "btnOK", "btnAbrir", "btnOpen" }, new[] { "Abrir", "Open" });
            _btnCancelar = FindButton(new[] { "btnCancelar", "btnCancel" }, new[] { "Cancelar", "Cancel" });

            // Si no encuentra una ListBox con esos nombres, usa la primera que haya
            _lst ??= this.Controls.OfType<ListBox>().FirstOrDefault()
                    ?? FindAllControls(this).OfType<ListBox>().FirstOrDefault();

            if (_lst == null)
                throw new InvalidOperationException("No se encontró un ListBox para mostrar los torneos. Nómbralo p.ej. 'lstTorneos' o deja cualquier ListBox y este código lo tomará.");

            // Conecta eventos y tecla Enter/Esc
            if (_btnOk != null)
            {
                _btnOk.Click -= btnOK_Click;
                _btnOk.Click += btnOK_Click;
                this.AcceptButton = _btnOk;
            }
            if (_btnCancelar != null)
            {
                _btnCancelar.Click -= btnCancelar_Click;
                _btnCancelar.Click += btnCancelar_Click;
                this.CancelButton = _btnCancelar;
            }

            _lst.DoubleClick -= lst_DoubleClick;
            _lst.DoubleClick += lst_DoubleClick;
            _lst.KeyDown -= lst_KeyDown;
            _lst.KeyDown += lst_KeyDown;

            // Cargar lista visual
            CargarLista();
        }

        // ===================== Carga de lista =====================
        private void CargarLista()
        {
            var items = _torneos.Select(t => new DisplayItem
            {
                Value = t,
                Text = $"{t.name} — {t.category}  (equipos: {t.teams.Count}, partidos: {t.matches.Count})"
            }).ToList();

            _lst!.DataSource = new BindingList<DisplayItem>(items);
            _lst.DisplayMember = nameof(DisplayItem.Text);
            _lst.ValueMember = nameof(DisplayItem.Value);

            if (_lst.Items.Count > 0)
                _lst.SelectedIndex = 0;
        }

        // ===================== Handlers =====================
        private void btnOK_Click(object? sender, EventArgs e)
        {
            if (_lst!.SelectedItem is not DisplayItem item)
            {
                MessageBox.Show("Selecciona un torneo.", "Aviso",
                    MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }

            SelectedTournament = item.Value;
            DialogResult = DialogResult.OK;
            Close();
        }

        private void btnCancelar_Click(object? sender, EventArgs e)
        {
            SelectedTournament = null;
            DialogResult = DialogResult.Cancel;
            Close();
        }

        private void lst_DoubleClick(object? sender, EventArgs e)
        {
            btnOK_Click(sender, e);
        }

        private void lst_KeyDown(object? sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                e.Handled = true;
                btnOK_Click(sender, e);
            }
        }

        // ===================== Utilidades de búsqueda =====================
        private static IEnumerable<Control> FindAllControls(Control root)
        {
            foreach (Control c in root.Controls)
            {
                foreach (var child in FindAllControls(c))
                    yield return child;
                yield return c;
            }
        }

        private Button? FindButton(string[] nameCandidates, string[] textCandidates)
        {
            var allButtons = FindAllControls(this).OfType<Button>();

            // por nombre
            var byName = allButtons.FirstOrDefault(b =>
                nameCandidates.Any(n => string.Equals(b.Name, n, StringComparison.OrdinalIgnoreCase)));
            if (byName != null) return byName;

            // por texto
            var byText = allButtons.FirstOrDefault(b =>
                textCandidates.Any(t => string.Equals(b.Text?.Trim(), t, StringComparison.OrdinalIgnoreCase)));
            return byText;
        }

        private ListBox? FindListBox(string[] nameCandidates)
        {
            // por nombre exacto
            var byName = FindAllControls(this).OfType<ListBox>().FirstOrDefault(lb =>
                nameCandidates.Any(n => string.Equals(lb.Name, n, StringComparison.OrdinalIgnoreCase)));
            return byName;
        }
    }
}
