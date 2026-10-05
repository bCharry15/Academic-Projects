package co.edu.unicauca.cliente.utilidades;

import java.io.InputStream;
import java.util.Properties;

public class UtilidadesConfiguracion
{
    private static final Properties propiedades = new Properties(); //Carga el archivo

    static
    {
        try (InputStream entrada = UtilidadesConfiguracion.class.getClassLoader()
                                      .getResourceAsStream("configuracion.properties"))
        {
            if (entrada == null)
            {
                System.out.println("No se encontro configuracion.properties");
            }
            else
            {
                propiedades.load(entrada);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error leyendo la configuracion: " + e.getMessage());
        }
    }

    public static String obtenerIpNS()
    {
        return propiedades.getProperty("ns.ip", "127.0.0.1");
    }

    public static int obtenerPuertoNS()
    {
        return Integer.parseInt(propiedades.getProperty("ns.puerto", "1099").trim());
    }

    public static String obtenerNombreObjeto()
    {
        return propiedades.getProperty("ns.objeto", "ServidorChat");
    }
}