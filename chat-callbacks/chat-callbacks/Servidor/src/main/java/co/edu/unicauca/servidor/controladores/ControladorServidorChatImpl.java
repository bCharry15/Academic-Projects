package co.edu.unicauca.servidor.controladores;

import co.edu.unicauca.cliente.controladores.UsuarioCllbckInt;
import co.edu.unicauca.servidor.servicios.GestorUsuarios;
import co.edu.unicauca.servidor.servicios.ValidadorDeUsuarios;
import co.edu.unicauca.servidor.servicios.MonitorDeUsuarios;
import co.edu.unicauca.servidor.utilidades.UtilidadesConfiguracion;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ControladorServidorChatImpl extends UnicastRemoteObject implements ControladorServidorChatInt
{
    private final GestorUsuarios gestor;
    private final ValidadorDeUsuarios validador;
    private final MonitorDeUsuarios monitor;

    public ControladorServidorChatImpl() throws RemoteException
    {
        super();
        this.gestor = new GestorUsuarios();
        this.validador = new ValidadorDeUsuarios(this.gestor);
        this.monitor = new MonitorDeUsuarios(this.validador, this.gestor,
        UtilidadesConfiguracion.obtenerIntervaloMonitor());
        this.monitor.start();
    }

    @Override
    public synchronized boolean registrarReferenciaUsuario(UsuarioCllbckInt usuario, String nickName)
            throws RemoteException
    {
        System.out.println("Solicitud de registro del nickName: " + nickName);
        boolean registrado = gestor.agregar(nickName, usuario);
        if (registrado)
        {
            System.out.println("Usuario registrado: " + nickName + " | activos: " + gestor.cantidad());
            enviarMensaje("sistema", nickName + " se unio al chat");
        }
        else
        {
            System.out.println("nickName rechazado (repetido o vacio): " + nickName);
        }
        return registrado;
    }

    @Override
    public synchronized List<String> listarUsuariosActivos() throws RemoteException //El controlador recibe la solicitud remota y delega en el gestor, que administra el mapa.
    {
        return gestor.listarNicks();
    }

    @Override
    public synchronized boolean cerrarSesion(String nickName) throws RemoteException
    {
        boolean eliminado = gestor.eliminar(nickName);
        if (eliminado)
        {
            System.out.println("Referencia remota eliminada por salida voluntaria: " + nickName);
            enviarMensaje("sistema", nickName + " salio del chat");
        }
        return eliminado;
    }

    @Override
    public synchronized int obtenerCantidadUsuariosActivos() throws RemoteException
    {
        return gestor.cantidad();
    }

    @Override
    public void enviarMensaje(String emisor, String mensaje) throws RemoteException
    {
        String texto = "[publico] " + emisor + ": " + mensaje;

        for (String nickReceptor : gestor.listarNicks())
        {
            UsuarioCllbckInt refReceptor = validador.validarReceptor(nickReceptor);
            if (refReceptor == null) { continue; }

            try
            {
                refReceptor.notificar(texto, gestor.cantidad());
            }
            catch (RemoteException e)
            {
                gestor.eliminar(nickReceptor);
                System.out.println("Referencia remota eliminada (receptor caido): " + nickReceptor);
            }
        }
    }

    @Override
    public void enviarMensajePrivado(String emisor, String destino, String mensaje) throws RemoteException
    {
        UsuarioCllbckInt refDestino = validador.validarReceptor(destino);

        if (refDestino != null)
        {
            try
            {
                refDestino.notificar("[privado de " + emisor + "] " + mensaje, gestor.cantidad());
                return;
            }
            catch (RemoteException e)
            {
                gestor.eliminar(destino);
                System.out.println("Referencia remota eliminada (receptor caido): " + destino);
            }
        }

        String MSG_NO_CONECTADO = "El mensaje no se logró enviar porque el usuario receptor no está conectado";
        UsuarioCllbckInt refEmisor = validador.validarReceptor(emisor);
        if (refEmisor != null)
        {
            try { refEmisor.notificar(MSG_NO_CONECTADO, gestor.cantidad()); }
            catch (RemoteException e) { gestor.eliminar(emisor); }
        }
    }
}