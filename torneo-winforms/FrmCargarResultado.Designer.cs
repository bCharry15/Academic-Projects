namespace TorneoWinForms
{
    partial class FrmCargarResultado
    {
        private System.ComponentModel.IContainer components = null;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label lblLocal;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label lblVisita;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.NumericUpDown nudHome;
        private System.Windows.Forms.NumericUpDown nudAway;
        private System.Windows.Forms.Button btnOk;
        private System.Windows.Forms.Button btnCancel;

        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null)) components.Dispose();
            base.Dispose(disposing);
        }

        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.lblLocal = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.lblVisita = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.nudHome = new System.Windows.Forms.NumericUpDown();
            this.nudAway = new System.Windows.Forms.NumericUpDown();
            this.btnOk = new System.Windows.Forms.Button();
            this.btnCancel = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.nudHome)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudAway)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(22, 22);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(35, 15);
            this.label1.TabIndex = 0;
            this.label1.Text = "Local:";
            // 
            // lblLocal
            // 
            this.lblLocal.AutoSize = true;
            this.lblLocal.Location = new System.Drawing.Point(63, 22);
            this.lblLocal.Name = "lblLocal";
            this.lblLocal.Size = new System.Drawing.Size(21, 15);
            this.lblLocal.TabIndex = 1;
            this.lblLocal.Text = "---";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(220, 22);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(39, 15);
            this.label2.TabIndex = 2;
            this.label2.Text = "Visita:";
            // 
            // lblVisita
            // 
            this.lblVisita.AutoSize = true;
            this.lblVisita.Location = new System.Drawing.Point(265, 22);
            this.lblVisita.Name = "lblVisita";
            this.lblVisita.Size = new System.Drawing.Size(21, 15);
            this.lblVisita.TabIndex = 3;
            this.lblVisita.Text = "---";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(22, 60);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(59, 15);
            this.label3.TabIndex = 4;
            this.label3.Text = "Resultado";
            // 
            // nudHome
            // 
            this.nudHome.Location = new System.Drawing.Point(100, 58);
            this.nudHome.Maximum = new decimal(new int[] { 100, 0, 0, 0 });
            this.nudHome.Name = "nudHome";
            this.nudHome.Size = new System.Drawing.Size(60, 23);
            this.nudHome.TabIndex = 5;
            // 
            // nudAway
            // 
            this.nudAway.Location = new System.Drawing.Point(180, 58);
            this.nudAway.Maximum = new decimal(new int[] { 100, 0, 0, 0 });
            this.nudAway.Name = "nudAway";
            this.nudAway.Size = new System.Drawing.Size(60, 23);
            this.nudAway.TabIndex = 6;
            // 
            // btnOk
            // 
            this.btnOk.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.btnOk.Location = new System.Drawing.Point(195, 101);
            this.btnOk.Name = "btnOk";
            this.btnOk.Size = new System.Drawing.Size(90, 27);
            this.btnOk.TabIndex = 7;
            this.btnOk.Text = "Aceptar";
            this.btnOk.UseVisualStyleBackColor = true;
            this.btnOk.Click += new System.EventHandler(this.btnOk_Click);
            // 
            // btnCancel
            // 
            this.btnCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.btnCancel.Location = new System.Drawing.Point(291, 101);
            this.btnCancel.Name = "btnCancel";
            this.btnCancel.Size = new System.Drawing.Size(90, 27);
            this.btnCancel.TabIndex = 8;
            this.btnCancel.Text = "Cancelar";
            this.btnCancel.UseVisualStyleBackColor = true;
            this.btnCancel.Click += new System.EventHandler(this.btnCancel_Click);
            // 
            // FrmCargarResultado
            // 
            this.AcceptButton = this.btnOk;
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(399, 145);
            this.Controls.Add(this.btnCancel);
            this.Controls.Add(this.btnOk);
            this.Controls.Add(this.nudAway);
            this.Controls.Add(this.nudHome);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.lblVisita);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.lblLocal);
            this.Controls.Add(this.label1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "FrmCargarResultado";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "Cargar resultado";
            ((System.ComponentModel.ISupportInitialize)(this.nudHome)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudAway)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();
        }
    }
}
