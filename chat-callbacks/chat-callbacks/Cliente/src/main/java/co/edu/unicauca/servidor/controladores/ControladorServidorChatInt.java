package co.edu.unicauca.servidor.controladores;

import co.edu.unicauca.cliente.controladores.UsuarioCllbckInt;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ControladorServidorChatInt extends Remote
{
    public boolean registrarReferenciaUsuario(UsuarioCllbckInt usuario, String nickName) throws RemoteException;
    public void enviarMensaje(String emisor, String mensaje) throws RemoteException;
    public List<String> listarUsuariosActivos() throws RemoteException;
    public boolean cerrarSesion(String nickName) throws RemoteException;
    public void enviarMensajePrivado(String emisor, String receptor, String mensaje) throws RemoteException;
    public int obtenerCantidadUsuariosActivos() throws RemoteException;
}


