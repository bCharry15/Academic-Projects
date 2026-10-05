package co.edu.unicauca.servidor.servicios;

import co.edu.unicauca.cliente.controladores.UsuarioCllbckInt;
import java.rmi.RemoteException;

public class ValidadorDeUsuarios
{
    private final GestorUsuarios gestor;

    public ValidadorDeUsuarios(GestorUsuarios gestor)
    {
        this.gestor = gestor;
    }

    public UsuarioCllbckInt validarReceptor(String nickNameReceptor)
    {
        UsuarioCllbckInt referencia = gestor.obtener(nickNameReceptor);
        if (referencia == null)
        {
            System.out.println("[validador] receptor no registrado: " + nickNameReceptor);
            return null;
        }
        try
        {
            referencia.estaActivo();
            return referencia;
        }
        catch (RemoteException e)
        {
            gestor.eliminar(nickNameReceptor);
            System.out.println("[validador] referencia remota eliminada (desconectado): " + nickNameReceptor);
            return null;
        }
    }

    public int depurarDesconectados()
    {
        int eliminados = 0;
        for (String nickName : gestor.listarNicks())
        {
            if (validarReceptor(nickName) == null) { eliminados++; }
        }
        return eliminados;
    }
}