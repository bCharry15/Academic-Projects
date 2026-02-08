namespace TorneoWinForms
{
    partial class FrmWelcome
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
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
            lblTitle = new Label();
            btnCrearTorneo = new Button();
            btnAbrirTorneo = new Button();
            btnSalir = new Button();
            SuspendLayout();
            // 
            // lblTitle
            // 
            lblTitle.Anchor = AnchorStyles.Top;
            lblTitle.Font = new Font("Segoe UI", 18F, FontStyle.Bold);
            lblTitle.Location = new Point(14, 12);
            lblTitle.Name = "lblTitle";
            lblTitle.Size = new Size(526, 53);
            lblTitle.TabIndex = 0;
            lblTitle.Text = "FOOTBALL TOURNAMENT";
            lblTitle.TextAlign = ContentAlignment.MiddleCenter;
            // 
            // btnCrearTorneo
            // 
            btnCrearTorneo.Location = new Point(105, 107);
            btnCrearTorneo.Margin = new Padding(3, 4, 3, 4);
            btnCrearTorneo.Name = "btnCrearTorneo";
            btnCrearTorneo.Size = new Size(343, 45);
            btnCrearTorneo.TabIndex = 1;
            btnCrearTorneo.Text = "CREATE TOURNAMENT";
            btnCrearTorneo.UseVisualStyleBackColor = true;
            btnCrearTorneo.Click += btnCrearTorneo_Click;
            // 
            // btnAbrirTorneo
            // 
            btnAbrirTorneo.Location = new Point(105, 171);
            btnAbrirTorneo.Margin = new Padding(3, 4, 3, 4);
            btnAbrirTorneo.Name = "btnAbrirTorneo";
            btnAbrirTorneo.Size = new Size(343, 45);
            btnAbrirTorneo.TabIndex = 2;
            btnAbrirTorneo.Text = "ENTER TOURNAMENT";
            btnAbrirTorneo.UseVisualStyleBackColor = true;
            btnAbrirTorneo.Click += btnAbrirTorneo_Click;
            // 
            // btnSalir
            // 
            btnSalir.Anchor = AnchorStyles.Bottom | AnchorStyles.Right;
            btnSalir.DialogResult = DialogResult.Cancel;
            btnSalir.Location = new Point(440, 252);
            btnSalir.Margin = new Padding(3, 4, 3, 4);
            btnSalir.Name = "btnSalir";
            btnSalir.Size = new Size(86, 36);
            btnSalir.TabIndex = 3;
            btnSalir.Text = "Exit";
            btnSalir.UseVisualStyleBackColor = true;
            btnSalir.Click += btnSalir_Click_1;
            // 
            // FrmWelcome
            // 
            AcceptButton = btnCrearTorneo;
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            CancelButton = btnSalir;
            ClientSize = new Size(539, 304);
            Controls.Add(btnSalir);
            Controls.Add(btnAbrirTorneo);
            Controls.Add(btnCrearTorneo);
            Controls.Add(lblTitle);
            FormBorderStyle = FormBorderStyle.FixedSingle;
            Margin = new Padding(3, 4, 3, 4);
            MaximizeBox = false;
            MinimizeBox = false;
            Name = "FrmWelcome";
            StartPosition = FormStartPosition.CenterScreen;
            Text = "Football Tournament";
            ResumeLayout(false);
        }

        #endregion

        private System.Windows.Forms.Label lblTitle;
        private System.Windows.Forms.Button btnCrearTorneo;
        private System.Windows.Forms.Button btnAbrirTorneo;
        private System.Windows.Forms.Button btnSalir;
    }
}
