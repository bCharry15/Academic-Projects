package co.edu.unicauca.cliente.controladores;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UsuarioCllbckImpl extends UnicastRemoteObject implements UsuarioCllbckInt
{
    private final String nickName;

    public UsuarioCllbckImpl(String nickName) throws RemoteException
    {
        super();
        this.nickName = nickName;
    }

    @Override
    public void notificar(String mensaje, int cantidadUsuarios) throws RemoteException
    {
        System.out.println("\n>> " + mensaje + "   (usuarios conectados: " + cantidadUsuarios + ")");
    }

    @Override
    public boolean estaActivo() throws RemoteException
    {
        return true;
    }

    @Override
    public String obtenerNickName() throws RemoteException
    {
        return nickName;
    }
}