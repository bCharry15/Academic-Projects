namespace TorneoWinForms
{
    partial class Form1
    {
        private System.ComponentModel.IContainer components = null;

        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        private void InitializeComponent()
        {
            lblTitulo = new Label();
            btnCrearTorneo = new Button();
            btnInscribirEquipo = new Button();
            btnProgramar = new Button();
            btnCargarResultado = new Button();
            btnEliminar = new Button();
            tabs = new TabControl();
            tabEquipos = new TabPage();
            dgvEquipos = new DataGridView();
            colPos = new DataGridViewTextBoxColumn();
            colEquipo = new DataGridViewTextBoxColumn();
            colPJ = new DataGridViewTextBoxColumn();
            colGF = new DataGridViewTextBoxColumn();
            colGC = new DataGridViewTextBoxColumn();
            colPts = new DataGridViewTextBoxColumn();
            tabPartidos = new TabPage();
            dgvPartidos = new DataGridView();
            colLocal = new DataGridViewTextBoxColumn();
            colVisitante = new DataGridViewTextBoxColumn();
            colResultado = new DataGridViewTextBoxColumn();
            tabs.SuspendLayout();
            tabEquipos.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)dgvEquipos).BeginInit();
            tabPartidos.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)dgvPartidos).BeginInit();
            SuspendLayout();
            // 
            // lblTitulo
            // 
            lblTitulo.Anchor = AnchorStyles.Top | AnchorStyles.Left | AnchorStyles.Right;
            lblTitulo.Font = new Font("Segoe UI", 16F, FontStyle.Bold);
            lblTitulo.Location = new Point(14, 12);
            lblTitulo.Name = "lblTitulo";
            lblTitulo.Size = new Size(983, 47);
            lblTitulo.TabIndex = 0;
            lblTitulo.Text = "Torneo: (sin seleccionar)";
            lblTitulo.TextAlign = ContentAlignment.MiddleLeft;
            // 
            // btnCrearTorneo
            // 
            btnCrearTorneo.Location = new Point(21, 75);
            btnCrearTorneo.Margin = new Padding(3, 4, 3, 4);
            btnCrearTorneo.Name = "btnCrearTorneo";
            btnCrearTorneo.Size = new Size(137, 40);
            btnCrearTorneo.TabIndex = 1;
            btnCrearTorneo.Text = "Crear Torneo";
            btnCrearTorneo.UseVisualStyleBackColor = true;
            // 
            // btnInscribirEquipo
            // 
            btnInscribirEquipo.Location = new Point(165, 75);
            btnInscribirEquipo.Margin = new Padding(3, 4, 3, 4);
            btnInscribirEquipo.Name = "btnInscribirEquipo";
            btnInscribirEquipo.Size = new Size(137, 40);
            btnInscribirEquipo.TabIndex = 2;
            btnInscribirEquipo.Text = "Inscribir equipo";
            btnInscribirEquipo.UseVisualStyleBackColor = true;
            // 
            // btnProgramar
            // 
            btnProgramar.Location = new Point(309, 75);
            btnProgramar.Margin = new Padding(3, 4, 3, 4);
            btnProgramar.Name = "btnProgramar";
            btnProgramar.Size = new Size(137, 40);
            btnProgramar.TabIndex = 3;
            btnProgramar.Text = "Programar";
            btnProgramar.UseVisualStyleBackColor = true;
            // 
            // btnCargarResultado
            // 
            btnCargarResultado.Location = new Point(453, 75);
            btnCargarResultado.Margin = new Padding(3, 4, 3, 4);
            btnCargarResultado.Name = "btnCargarResultado";
            btnCargarResultado.Size = new Size(149, 40);
            btnCargarResultado.TabIndex = 4;
            btnCargarResultado.Text = "Cargar resultado";
            btnCargarResultado.UseVisualStyleBackColor = true;
            btnCargarResultado.Click += btnCargarResultado_Click_1;
            // 
            // btnEliminar
            // 
            btnEliminar.Anchor = AnchorStyles.Top | AnchorStyles.Right;
            btnEliminar.Location = new Point(859, 75);
            btnEliminar.Margin = new Padding(3, 4, 3, 4);
            btnEliminar.Name = "btnEliminar";
            btnEliminar.Size = new Size(137, 40);
            btnEliminar.TabIndex = 5;
            btnEliminar.Text = "Eliminar";
            btnEliminar.UseVisualStyleBackColor = true;
            // 
            // tabs
            // 
            tabs.Anchor = AnchorStyles.Top | AnchorStyles.Bottom | AnchorStyles.Left | AnchorStyles.Right;
            tabs.Controls.Add(tabEquipos);
            tabs.Controls.Add(tabPartidos);
            tabs.Location = new Point(14, 133);
            tabs.Margin = new Padding(3, 4, 3, 4);
            tabs.Name = "tabs";
            tabs.SelectedIndex = 0;
            tabs.Size = new Size(983, 573);
            tabs.TabIndex = 6;
            // 
            // tabEquipos
            // 
            tabEquipos.Controls.Add(dgvEquipos);
            tabEquipos.Location = new Point(4, 29);
            tabEquipos.Margin = new Padding(3, 4, 3, 4);
            tabEquipos.Name = "tabEquipos";
            tabEquipos.Padding = new Padding(3, 4, 3, 4);
            tabEquipos.Size = new Size(975, 540);
            tabEquipos.TabIndex = 0;
            tabEquipos.Text = "Equipos";
            tabEquipos.UseVisualStyleBackColor = true;
            // 
            // dgvEquipos
            // 
            dgvEquipos.AllowUserToAddRows = false;
            dgvEquipos.AllowUserToDeleteRows = false;
            dgvEquipos.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dgvEquipos.Columns.AddRange(new DataGridViewColumn[] { colPos, colEquipo, colPJ, colGF, colGC, colPts });
            dgvEquipos.Dock = DockStyle.Fill;
            dgvEquipos.Location = new Point(3, 4);
            dgvEquipos.Margin = new Padding(3, 4, 3, 4);
            dgvEquipos.MultiSelect = false;
            dgvEquipos.Name = "dgvEquipos";
            dgvEquipos.ReadOnly = true;
            dgvEquipos.RowHeadersVisible = false;
            dgvEquipos.RowHeadersWidth = 51;
            dgvEquipos.RowTemplate.Height = 25;
            dgvEquipos.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            dgvEquipos.Size = new Size(969, 532);
            dgvEquipos.TabIndex = 0;
            dgvEquipos.CellContentClick += dgvEquipos_CellContentClick;
            // 
            // colPos
            // 
            colPos.HeaderText = "Pos";
            colPos.MinimumWidth = 6;
            colPos.Name = "colPos";
            colPos.ReadOnly = true;
            colPos.Width = 50;
            // 
            // colEquipo
            // 
            colEquipo.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            colEquipo.HeaderText = "Equipo";
            colEquipo.MinimumWidth = 6;
            colEquipo.Name = "colEquipo";
            colEquipo.ReadOnly = true;
            // 
            // colPJ
            // 
            colPJ.HeaderText = "PJ";
            colPJ.MinimumWidth = 6;
            colPJ.Name = "colPJ";
            colPJ.ReadOnly = true;
            colPJ.Width = 60;
            // 
            // colGF
            // 
            colGF.HeaderText = "GF";
            colGF.MinimumWidth = 6;
            colGF.Name = "colGF";
            colGF.ReadOnly = true;
            colGF.Width = 60;
            // 
            // colGC
            // 
            colGC.HeaderText = "GC";
            colGC.MinimumWidth = 6;
            colGC.Name = "colGC";
            colGC.ReadOnly = true;
            colGC.Width = 60;
            // 
            // colPts
            // 
            colPts.HeaderText = "Pts";
            colPts.MinimumWidth = 6;
            colPts.Name = "colPts";
            colPts.ReadOnly = true;
            colPts.Width = 60;
            // 
            // tabPartidos
            // 
            tabPartidos.Controls.Add(dgvPartidos);
            tabPartidos.Location = new Point(4, 29);
            tabPartidos.Margin = new Padding(3, 4, 3, 4);
            tabPartidos.Name = "tabPartidos";
            tabPartidos.Padding = new Padding(3, 4, 3, 4);
            tabPartidos.Size = new Size(975, 540);
            tabPartidos.TabIndex = 1;
            tabPartidos.Text = "Partidos";
            tabPartidos.UseVisualStyleBackColor = true;
            // 
            // dgvPartidos
            // 
            dgvPartidos.AllowUserToAddRows = false;
            dgvPartidos.AllowUserToDeleteRows = false;
            dgvPartidos.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dgvPartidos.Columns.AddRange(new DataGridViewColumn[] { colLocal, colVisitante, colResultado });
            dgvPartidos.Dock = DockStyle.Fill;
            dgvPartidos.Location = new Point(3, 4);
            dgvPartidos.Margin = new Padding(3, 4, 3, 4);
            dgvPartidos.MultiSelect = false;
            dgvPartidos.Name = "dgvPartidos";
            dgvPartidos.ReadOnly = true;
            dgvPartidos.RowHeadersVisible = false;
            dgvPartidos.RowHeadersWidth = 51;
            dgvPartidos.RowTemplate.Height = 25;
            dgvPartidos.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            dgvPartidos.Size = new Size(969, 532);
            dgvPartidos.TabIndex = 0;
            // 
            // colLocal
            // 
            colLocal.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            colLocal.HeaderText = "Local";
            colLocal.MinimumWidth = 6;
            colLocal.Name = "colLocal";
            colLocal.ReadOnly = true;
            // 
            // colVisitante
            // 
            colVisitante.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            colVisitante.HeaderText = "Visitante";
            colVisitante.MinimumWidth = 6;
            colVisitante.Name = "colVisitante";
            colVisitante.ReadOnly = true;
            // 
            // colResultado
            // 
            colResultado.HeaderText = "Resultado";
            colResultado.MinimumWidth = 6;
            colResultado.Name = "colResultado";
            colResultado.ReadOnly = true;
            colResultado.Width = 120;
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(1010, 721);
            Controls.Add(tabs);
            Controls.Add(btnEliminar);
            Controls.Add(btnCargarResultado);
            Controls.Add(btnProgramar);
            Controls.Add(btnInscribirEquipo);
            Controls.Add(btnCrearTorneo);
            Controls.Add(lblTitulo);
            Margin = new Padding(3, 4, 3, 4);
            Name = "Form1";
            StartPosition = FormStartPosition.CenterScreen;
            Text = "Gestor de Torneo";
            tabs.ResumeLayout(false);
            tabEquipos.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)dgvEquipos).EndInit();
            tabPartidos.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)dgvPartidos).EndInit();
            ResumeLayout(false);
        }

        #endregion

        private System.Windows.Forms.Label lblTitulo;
        private System.Windows.Forms.Button btnCrearTorneo;
        private System.Windows.Forms.Button btnInscribirEquipo;
        private System.Windows.Forms.Button btnProgramar;
        private System.Windows.Forms.Button btnCargarResultado;
        private System.Windows.Forms.Button btnEliminar;
        private System.Windows.Forms.TabControl tabs;
        private System.Windows.Forms.TabPage tabEquipos;
        private System.Windows.Forms.DataGridView dgvEquipos;
        private System.Windows.Forms.DataGridViewTextBoxColumn colPos;
        private System.Windows.Forms.DataGridViewTextBoxColumn colEquipo;
        private System.Windows.Forms.DataGridViewTextBoxColumn colPJ;
        private System.Windows.Forms.DataGridViewTextBoxColumn colGF;
        private System.Windows.Forms.DataGridViewTextBoxColumn colGC;
        private System.Windows.Forms.DataGridViewTextBoxColumn colPts;
        private System.Windows.Forms.TabPage tabPartidos;
        private System.Windows.Forms.DataGridView dgvPartidos;
        private System.Windows.Forms.DataGridViewTextBoxColumn colLocal;
        private System.Windows.Forms.DataGridViewTextBoxColumn colVisitante;
        private System.Windows.Forms.DataGridViewTextBoxColumn colResultado;
    }
}
