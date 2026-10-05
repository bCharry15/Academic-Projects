package co.edu.unicauca.servidor.servicios;

import java.rmi.RemoteException;

import co.edu.unicauca.servidor.controladores.ControladorServidorChatImpl;
import co.edu.unicauca.servidor.utilidades.UtilidadesConfiguracion;
import co.edu.unicauca.servidor.utilidades.UtilidadesRegistroS;

public class ServidorDeObjetos
{
    public static void main(String args[]) throws RemoteException
    {
        String direccionIpRMIRegistry = UtilidadesConfiguracion.obtenerIpNS(); // el servidor usa esos metodso para arrancar
        int numPuertoRMIRegistry = UtilidadesConfiguracion.obtenerPuertoNS();

        System.out.println("Configuracion leida del properties -> " + direccionIpRMIRegistry + ":" + numPuertoRMIRegistry);

        ControladorServidorChatImpl objRemoto = new ControladorServidorChatImpl();

        try
        {
            UtilidadesRegistroS.arrancarNS(numPuertoRMIRegistry);
            UtilidadesRegistroS.RegistrarObjetoRemoto(objRemoto, direccionIpRMIRegistry, numPuertoRMIRegistry, "ServidorChat");
            System.out.println("Servidor esperando conexiones... Presiona Ctrl+C para terminar");
            try { Thread.currentThread().join(); } catch (InterruptedException e) { }
        }
        catch (Exception e)
        {
            System.err.println("No fue posible Arrancar el NS o Registrar el objeto remoto" + e.getMessage());
        }
    }
}