using System;
using System.Drawing;
using System.Windows.Forms;
using TorneoWinForms.Modelos;
using TorneoWinForms.Servicios;

namespace TorneoWinForms
{
    public class FrmAuth : Form
    {
        public User? LoggedInUser { get; private set; }

        private readonly TabControl tabs = new();
        private readonly TabPage tabLogin = new() { Text = "Log in" };
        private readonly TabPage tabSignup = new() { Text = "Sign up" };

        // Login
        private TextBox txtLoginEmail = null!;
        private TextBox txtLoginPassword = null!;
        private Button btnLogin = null!;
        private Panel _loginPanel = null!;
        private Label _lblPromoLogin = null!;

        // SignUp
        private TextBox txtName = null!;
        private TextBox txtEmail = null!;
        private TextBox txtPassword = null!;
        private Button btnSignup = null!;
        private Panel _signupPanel = null!;
        private Label _lblSignupTitle = null!;
        private Label _lblPromo = null!;

        public FrmAuth()
        {
            BuildUI();
        }

        private void BuildUI()
        {
            StartPosition = FormStartPosition.CenterScreen;
            Text = "Football Tournament - Autenticación";
            ClientSize = new Size(440, 620);
            FormBorderStyle = FormBorderStyle.FixedSingle;
            MaximizeBox = false;

            SetStyle(ControlStyles.OptimizedDoubleBuffer | ControlStyles.AllPaintingInWmPaint, true);
            UpdateStyles();

            try
            {
                this.BackgroundImage = global::TorneoWinForms.Properties.Resources.UEFA_Champions_League_Mobile_Background___Foto_di_calcio__Immagini_di_calcio__Sfondi;
                this.BackgroundImageLayout = ImageLayout.Stretch;
            }
            catch { }

            var card = new Panel
            {
                BackColor = Color.FromArgb(240, 245, 240),
                Padding = new Padding(16),
                Size = new Size(380, 520),
                Location = new Point((ClientSize.Width - 380) / 2, (ClientSize.Height - 520) / 2)
            };
            card.Anchor = AnchorStyles.None;
            Controls.Add(card);

            tabs.Size = new Size(348, 488);
            tabs.Location = new Point(16, 16);
            tabs.ItemSize = new Size(120, 30);
            tabs.SizeMode = TabSizeMode.Fixed;
            tabs.Font = new Font("Segoe UI", 10, FontStyle.Bold);
            tabs.TabPages.Add(tabLogin);
            tabs.TabPages.Add(tabSignup);
            card.Controls.Add(tabs);

            BuildLoginUI();
            BuildSignupUI();
        }

        private void BuildLoginUI()
        {
            _loginPanel = new Panel { Dock = DockStyle.Fill, BackColor = Color.White };
            tabLogin.Controls.Add(_loginPanel);

            var lblTitle = new Label
            {
                Text = "Bienvenido a\nFootball Tournament",
                AutoSize = false,
                TextAlign = ContentAlignment.MiddleCenter,
                Dock = DockStyle.Top,
                Height = 100,
                Font = new Font("Segoe UI", 14, FontStyle.Bold)
            };
            _loginPanel.Controls.Add(lblTitle);

            // --- Mensaje promocional en Login (pequeño y claro) ---
            _lblPromoLogin = new Label
            {
                AutoSize = false,
                BackColor = Color.Transparent,
                TextAlign = ContentAlignment.MiddleCenter,
                Font = new Font("Segoe UI", 9f, FontStyle.Regular),
                ForeColor = Color.FromArgb(110, 110, 110),
                Dock = DockStyle.Top,
                Padding = new Padding(5, 0, 5, 5),
                Text = "Organiza ligas, programa partidos y lleva la tabla de posiciones en minutos."
            };
            _loginPanel.Controls.Add(_lblPromoLogin);

            var container = new TableLayoutPanel
            {
                Dock = DockStyle.Top,
                Height = 220,
                ColumnCount = 1,
                RowCount = 6,
                Padding = new Padding(12)
            };
            container.RowStyles.Clear();
            for (int i = 0; i < 6; i++)
                container.RowStyles.Add(new RowStyle(SizeType.Absolute, 35));
            _loginPanel.Controls.Add(container);

            container.Controls.Add(new Label { Text = "E-mail", Dock = DockStyle.Fill });
            txtLoginEmail = new TextBox { Dock = DockStyle.Fill, PlaceholderText = "correo@ejemplo.com" };
            container.Controls.Add(txtLoginEmail);

            container.Controls.Add(new Label { Text = "Contraseña", Dock = DockStyle.Fill });
            txtLoginPassword = new TextBox { Dock = DockStyle.Fill, UseSystemPasswordChar = true, PlaceholderText = "******" };
            container.Controls.Add(txtLoginPassword);

            btnLogin = new Button
            {
                Text = "Iniciar sesión",
                Height = 36,
                Dock = DockStyle.Top,
                BackColor = Color.FromArgb(220, 55, 55),
                ForeColor = Color.White,
                FlatStyle = FlatStyle.Flat
            };
            btnLogin.FlatAppearance.BorderSize = 0;
            btnLogin.Click += BtnLogin_Click;
            _loginPanel.Controls.Add(btnLogin);

            AcceptButton = btnLogin;

            _loginPanel.Resize += (_, __) => LayoutPromoLogin();
            LayoutPromoLogin();
        }

        private void BuildSignupUI()
        {
            _signupPanel = new Panel { Dock = DockStyle.Fill, BackColor = Color.White };
            tabSignup.Controls.Add(_signupPanel);

            _lblSignupTitle = new Label
            {
                Text = "Crea tu cuenta",
                AutoSize = false,
                TextAlign = ContentAlignment.MiddleCenter,
                Dock = DockStyle.Top,
                Height = 60,
                Font = new Font("Segoe UI", 14, FontStyle.Bold)
            };
            _signupPanel.Controls.Add(_lblSignupTitle);

            // --- Descripción promocional en Sign up (pequeña y clara) ---
            _lblPromo = new Label
            {
                AutoSize = false,
                BackColor = Color.Transparent,
                TextAlign = ContentAlignment.MiddleCenter,
                Font = new Font("Segoe UI", 9f, FontStyle.Regular),
                ForeColor = Color.FromArgb(110, 110, 110),
                Dock = DockStyle.Top,
                Padding = new Padding(5, 0, 5, 5),
                Text = "Organiza ligas, programa partidos y lleva la tabla de posiciones en minutos.\n¡Todo desde un solo lugar y gratis!"
            };
            _signupPanel.Controls.Add(_lblPromo);

            var container = new TableLayoutPanel
            {
                Dock = DockStyle.Top,
                Height = 260,
                ColumnCount = 1,
                RowCount = 8,
                Padding = new Padding(12)
            };
            container.RowStyles.Clear();
            for (int i = 0; i < 8; i++)
                container.RowStyles.Add(new RowStyle(SizeType.Absolute, 35));
            _signupPanel.Controls.Add(container);

            container.Controls.Add(new Label { Text = "Nombre", Dock = DockStyle.Fill });
            txtName = new TextBox { Dock = DockStyle.Fill, PlaceholderText = "Tu nombre" };
            container.Controls.Add(txtName);

            container.Controls.Add(new Label { Text = "E-mail", Dock = DockStyle.Fill });
            txtEmail = new TextBox { Dock = DockStyle.Fill, PlaceholderText = "correo@ejemplo.com" };
            container.Controls.Add(txtEmail);

            container.Controls.Add(new Label { Text = "Contraseña", Dock = DockStyle.Fill });
            txtPassword = new TextBox { Dock = DockStyle.Fill, UseSystemPasswordChar = true, PlaceholderText = "mín. 6 caracteres" };
            container.Controls.Add(txtPassword);

            btnSignup = new Button
            {
                Text = "Crear cuenta",
                Height = 36,
                Dock = DockStyle.Top,
                BackColor = Color.FromArgb(55, 140, 55),
                ForeColor = Color.White,
                FlatStyle = FlatStyle.Flat
            };
            btnSignup.FlatAppearance.BorderSize = 0;
            btnSignup.Click += BtnSignup_Click;
            _signupPanel.Controls.Add(btnSignup);

            _signupPanel.Resize += (_, __) => LayoutPromoLabel();
            LayoutPromoLabel();
        }

        // Ajuste de envoltura y alto del texto promocional (Sign up)
        private void LayoutPromoLabel()
        {
            if (_signupPanel is null || _lblPromo is null) return;

            int innerWidth = _signupPanel.ClientSize.Width - _lblPromo.Padding.Horizontal - 24;
            innerWidth = Math.Max(40, innerWidth);

            _lblPromo.MaximumSize = new Size(innerWidth, 0);
            _lblPromo.Width = innerWidth;

            _lblPromo.Height = TextRenderer.MeasureText(
                _lblPromo.Text,
                _lblPromo.Font,
                new Size(_lblPromo.Width, int.MaxValue),
                TextFormatFlags.WordBreak | TextFormatFlags.NoPadding
            ).Height + 6;
        }

        // Ajuste de envoltura y alto del texto promocional (Login)
        private void LayoutPromoLogin()
        {
            if (_loginPanel is null || _lblPromoLogin is null) return;

            int innerWidth = _loginPanel.ClientSize.Width - _lblPromoLogin.Padding.Horizontal - 24;
            innerWidth = Math.Max(40, innerWidth);

            _lblPromoLogin.MaximumSize = new Size(innerWidth, 0);
            _lblPromoLogin.Width = innerWidth;

            _lblPromoLogin.Height = TextRenderer.MeasureText(
                _lblPromoLogin.Text,
                _lblPromoLogin.Font,
                new Size(_lblPromoLogin.Width, int.MaxValue),
                TextFormatFlags.WordBreak | TextFormatFlags.NoPadding
            ).Height + 6;
        }

        private void BtnLogin_Click(object? sender, EventArgs e)
        {
            try
            {
                var u = AuthService.Login(txtLoginEmail.Text, txtLoginPassword.Text);
                LoggedInUser = u;
                DialogResult = DialogResult.OK;
                Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "No se pudo iniciar sesión",
                    MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
        }

        private void BtnSignup_Click(object? sender, EventArgs e)
        {
            try
            {
                var u = AuthService.Register(txtName.Text, txtEmail.Text, txtPassword.Text);
                MessageBox.Show("Cuenta creada correctamente. Ya puedes iniciar sesión.",
                    "OK", MessageBoxButtons.OK, MessageBoxIcon.Information);
                tabs.SelectedTab = tabLogin;
                txtLoginEmail.Text = u.Email;
                txtLoginPassword.Clear();
                txtLoginPassword.Focus();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "No se pudo crear la cuenta",
                    MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
        }
    }
}
