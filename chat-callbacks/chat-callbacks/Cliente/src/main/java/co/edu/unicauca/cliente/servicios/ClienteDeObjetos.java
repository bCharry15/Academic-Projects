package co.edu.unicauca.cliente.servicios;

import co.edu.unicauca.cliente.controladores.UsuarioCllbckImpl;
import co.edu.unicauca.cliente.utilidades.UtilidadesConsola;
import co.edu.unicauca.cliente.utilidades.UtilidadesConfiguracion;
import co.edu.unicauca.cliente.utilidades.UtilidadesRegistroC;
import co.edu.unicauca.servidor.controladores.ControladorServidorChatInt;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ClienteDeObjetos
{
    public static void main(String[] args)
    {
        try
        {
            String ip = UtilidadesConfiguracion.obtenerIpNS();
            int puerto = UtilidadesConfiguracion.obtenerPuertoNS();

            System.out.println("Conectando a " + ip + ":" + puerto);

            ControladorServidorChatInt servidor = (ControladorServidorChatInt)
                    UtilidadesRegistroC.obtenerObjRemoto(puerto, ip, UtilidadesConfiguracion.obtenerNombreObjeto());

            if (servidor == null)
            {
                System.out.println("No se pudo obtener el ServidorChat");
                return;
            }

            String nickName = "";
            boolean registrado = false;
            do
            {
                System.out.println("Digite su nickName: ");
                nickName = UtilidadesConsola.leerCadena();
                
                UsuarioCllbckImpl objNuevoUsuario = new UsuarioCllbckImpl(nickName);
                registrado = servidor.registrarReferenciaUsuario(objNuevoUsuario, nickName);
                
                if (!registrado)
                {
                    System.out.println("Ese nickName ya esta en uso o es invalido. Intente con otro.");
                }
                else
                {
                    System.out.println("Registrado en el chat como: " + nickName);
                    menuPrincipal(servidor, nickName, objNuevoUsuario);
                    break;
                }
            } while (!registrado);
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void menuPrincipal(ControladorServidorChatInt servidor, String nickName, UsuarioCllbckImpl objNuevoUsuario)
            throws Exception
    {
        boolean salir = false;
        while (!salir)
        {
            System.out.println("\n===== CHAT RMI (" + nickName + ") =====");
            System.out.println("1. Enviar mensaje publico");
            System.out.println("2. Enviar mensaje privado");
            System.out.println("3. Ver usuarios activos");
            System.out.println("4. Ver cantidad de usuarios activos");
            System.out.println("5. Salir del chat");
            int opcion = UtilidadesConsola.leerEntero();

            switch (opcion)
            {
                case 1:
                    System.out.println("Escriba el mensaje: ");
                    String mensajePublico = UtilidadesConsola.leerCadena();
                    servidor.enviarMensaje(nickName, mensajePublico);
                    break;

                case 2:
                    System.out.println("A quien desea escribir? (nickName)");
                    String destino = UtilidadesConsola.leerCadena();
                    System.out.println("Escriba el mensaje: ");
                    String mensajePrivado = UtilidadesConsola.leerCadena();
                    servidor.enviarMensajePrivado(nickName, destino, mensajePrivado);
                    break;

                case 3:
                    List<String> activos = servidor.listarUsuariosActivos();
                    System.out.println("Usuarios activos (" + activos.size() + "):");
                    for (String nick : activos)
                    {
                        System.out.println(" - " + nick);
                    }
                    break;

                case 4:
                    int cantidad = servidor.obtenerCantidadUsuariosActivos();
                    System.out.println("Usuarios activos: " + cantidad);
                    break;

                case 5:
                    servidor.cerrarSesion(nickName);
                    UnicastRemoteObject.unexportObject(objNuevoUsuario, true);
                    System.out.println("Sesion cerrada. Hasta luego, " + nickName);
                    salir = true;
                    break;

                default:
                    System.out.println("Opcion invalida");
            }
        }
    }
}