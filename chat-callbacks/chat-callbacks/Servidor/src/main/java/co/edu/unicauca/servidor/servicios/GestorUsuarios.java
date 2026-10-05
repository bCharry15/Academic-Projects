package co.edu.unicauca.servidor.servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.edu.unicauca.cliente.controladores.UsuarioCllbckInt;

public class GestorUsuarios 
{
    private final HashMap<String, UsuarioCllbckInt> usuarios = new HashMap<>(); //Los usuarios están guardados

    public synchronized boolean agregar(String nickName, UsuarioCllbckInt referencia)
    {
        if (nickName == null || nickName.trim().isEmpty()) { return false; }
        if (usuarios.containsKey(nickName)) { return false; }
        usuarios.put(nickName, referencia);
        return true;
    }

    public synchronized boolean eliminar(String nickName)
    {
        return usuarios.remove(nickName) != null;
    }

    public synchronized boolean existe(String nickName)
    {
        return usuarios.containsKey(nickName);
    }

    public synchronized UsuarioCllbckInt obtener(String nickName)
    {
        return usuarios.get(nickName);
    }

    public synchronized int cantidad()
    {
        return usuarios.size();
    }

    public synchronized List<String> listarNicks() //Para obtener solamente los nicknames regostrados
    {
        return new ArrayList<>(usuarios.keySet());
    }
}