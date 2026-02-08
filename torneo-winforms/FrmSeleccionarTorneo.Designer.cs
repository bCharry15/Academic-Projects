// FrmSeleccionarTorneo.Designer.cs
using System.Windows.Forms;

namespace TorneoWinForms
{
    partial class FrmSeleccionarTorneo
    {
        private System.ComponentModel.IContainer components = null;
        private ListBox lstTorneos;
        private Button btnAbrir;
        private Button btnCancelar;

        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null)) components.Dispose();
            base.Dispose(disposing);
        }

        private void InitializeComponent()
        {
            components = new System.ComponentModel.Container();

            lstTorneos = new ListBox();
            btnAbrir = new Button();
            btnCancelar = new Button();

            SuspendLayout();

            // lstTorneos
            lstTorneos.Name = "lstTorneos";
            lstTorneos.Location = new System.Drawing.Point(12, 12);
            lstTorneos.Size = new System.Drawing.Size(320, 184);
            lstTorneos.Anchor = AnchorStyles.Top | AnchorStyles.Left | AnchorStyles.Right | AnchorStyles.Bottom;

            // btnAbrir
            btnAbrir.Name = "btnAbrir";
            btnAbrir.Text = "Abrir";
            btnAbrir.Location = new System.Drawing.Point(176, 205);
            btnAbrir.Size = new System.Drawing.Size(75, 28);
            btnAbrir.Anchor = AnchorStyles.Bottom | AnchorStyles.Right;

            // btnCancelar
            btnCancelar.Name = "btnCancelar";
            btnCancelar.Text = "Cancelar";
            btnCancelar.Location = new System.Drawing.Point(257, 205);
            btnCancelar.Size = new System.Drawing.Size(75, 28);
            btnCancelar.Anchor = AnchorStyles.Bottom | AnchorStyles.Right;

            // FrmSeleccionarTorneo
            AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new System.Drawing.Size(344, 241);
            Controls.Add(lstTorneos);
            Controls.Add(btnAbrir);
            Controls.Add(btnCancelar);
            Name = "FrmSeleccionarTorneo";
            StartPosition = FormStartPosition.CenterParent;
            Text = "Seleccionar Torneo";

            ResumeLayout(false);
        }
    }
}
