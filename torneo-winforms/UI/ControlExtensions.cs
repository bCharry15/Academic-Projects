using System.Reflection;
using System.Windows.Forms;

namespace TorneoWinForms.UI
{
    public static class ControlExtensions
    {
        public static void EnableDoubleBuffering(this Control control)
        {
            typeof(Control)
                .GetProperty("DoubleBuffered", BindingFlags.Instance | BindingFlags.NonPublic)!
                .SetValue(control, true, null);
        }
    }
}
