// UI/SoccerTheme.cs
using System.Drawing;
using System.Windows.Forms;

public static class SoccerTheme
{
    public static void Apply(Form f)
    {
        f.BackColor = Color.FromArgb(245, 247, 250);

        foreach (Control c in f.Controls) StyleControl(c);
    }

    private static void StyleControl(Control c)
    {
        if (c is Button b)
        {
            b.FlatStyle = FlatStyle.Flat;
            b.FlatAppearance.BorderSize = 0;
            b.BackColor = Color.FromArgb(35, 134, 54);
            b.ForeColor = Color.White;
            b.Padding = new Padding(8, 5, 8, 5);
            b.Cursor = Cursors.Hand;
        }
        else if (c is Label l)
        {
            l.ForeColor = Color.FromArgb(35, 35, 35);
        }
        else if (c is TabControl tc)
        {
            tc.Padding = new Point(12, 6);
            foreach (TabPage p in tc.TabPages)
            {
                p.BackColor = Color.White;
                foreach (Control sub in p.Controls) StyleControl(sub);
            }
        }
        else if (c is DataGridView dgv)
        {
            dgv.BackgroundColor = Color.White;
            dgv.BorderStyle = BorderStyle.None;
            dgv.EnableHeadersVisualStyles = false;
            dgv.ColumnHeadersDefaultCellStyle.BackColor = Color.FromArgb(23, 92, 43);
            dgv.ColumnHeadersDefaultCellStyle.ForeColor = Color.White;
            dgv.ColumnHeadersDefaultCellStyle.Font = new Font("Segoe UI Semibold", 9f);
            dgv.DefaultCellStyle.SelectionBackColor = Color.FromArgb(229, 243, 231);
            dgv.DefaultCellStyle.SelectionForeColor = Color.Black;
            dgv.GridColor = Color.FromArgb(220, 230, 220);
        }

        foreach (Control sub in c.Controls) StyleControl(sub);
    }
}
