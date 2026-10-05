

package co.edu.unicauca.servidor.controladores;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import co.edu.unicauca.cliente.controladores.UsuarioCllbckInt;

public interface ControladorServidorChatInt extends Remote
{
    public boolean registrarReferenciaUsuario(UsuarioCllbckInt usuario, String nickName) throws RemoteException;
    public void enviarMensaje(String emisor, String mensaje) throws RemoteException;
    public List<String> listarUsuariosActivos() throws RemoteException; //Esta declaración permite que el cliente solicite la lista mediante RMI.
    public void enviarMensajePrivado(String emisor, String destino, String mensaje) throws RemoteException;
    public int obtenerCantidadUsuariosActivos() throws RemoteException;
    public boolean cerrarSesion(String nickName) throws RemoteException;
}


