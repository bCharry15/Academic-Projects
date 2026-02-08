using TorneoWinForms.Modelos;

namespace TorneoWinForms.Datos
{
    public static class TorneoActual
    {
        public static Tournament? Torneo { get; set; }
        public static bool HayTorneo => Torneo is not null;

        private static int _idEquipo = 1;
        private static int _idPartido = 1;

        public static int NuevoIdEquipo() => _idEquipo++;
        public static int NuevoIdPartido() => _idPartido++;
    }

}
