using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

namespace TorneoWinForms.UI
{
    public class GradientPanel : Panel
    {
        public Color Color1 { get; set; } = Color.FromArgb(60, 0, 0, 0);
        public Color Color2 { get; set; } = Color.FromArgb(140, 0, 0, 0);
        public float Angle { get; set; } = 90f;

        public GradientPanel() { DoubleBuffered = true; ResizeRedraw = true; }

        protected override void OnPaintBackground(PaintEventArgs e)
        {
            using var brush = new LinearGradientBrush(ClientRectangle, Color1, Color2, Angle);
            e.Graphics.FillRectangle(brush, ClientRectangle);
        }
    }
}
