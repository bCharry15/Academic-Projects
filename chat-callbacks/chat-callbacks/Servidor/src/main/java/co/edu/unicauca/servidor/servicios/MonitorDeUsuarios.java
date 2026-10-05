package co.edu.unicauca.servidor.servicios;

public class MonitorDeUsuarios extends Thread
{
    private final ValidadorDeUsuarios validador;
    private final GestorUsuarios gestor;
    private final int intervaloSegundos;
    private volatile boolean enEjecucion = true;

    public MonitorDeUsuarios(ValidadorDeUsuarios validador, GestorUsuarios gestor, int intervaloSegundos)
    {
        this.validador = validador;
        this.gestor = gestor;
        this.intervaloSegundos = intervaloSegundos;
        this.setName("MonitorDeUsuarios");
        this.setDaemon(true);
    }

    public void detener()
    {
        this.enEjecucion = false;
        this.interrupt();
    }

    @Override
    public void run()
    {
        System.out.println("[monitor] iniciado, revisando cada " + intervaloSegundos + " segundos");
        while (enEjecucion)
        {
            try
            {
                Thread.sleep(intervaloSegundos * 1000L);
                int eliminados = validador.depurarDesconectados();
                System.out.println("[monitor] revision periodica -> eliminados: " + eliminados
                                   + " | usuarios activos: " + gestor.cantidad());
            }
            catch (InterruptedException e)
            {
                System.out.println("[monitor] detenido");
                return;
            }
        }
    }
}