namespace TorneoWinForms.Modelos
{
    public enum Categoria
    {
        SinDefinir = 0,
        Infantil,
        Juvenil,
        Mayores,
        Femenino,        Libre
    }

    public class Tournament
    {
        public string name { get; set; } = "";
        public Categoria category { get; set; } = Categoria.SinDefinir;

        public List<Team> teams { get; } = new();
        public List<Match> matches { get; } = new();
    }
}
