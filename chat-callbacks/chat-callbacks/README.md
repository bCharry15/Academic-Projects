# Chat con callbacks — Java RMI

Proyecto de Sistemas Distribuidos que implementa un chat por consola. Utiliza Java RMI y callbacks para comunicar al servidor con varios clientes.

# Funcionalidades
- Registro con nickname único.
- Mensajes públicos y privados.
- Consulta de usuarios y cantidad de conectados.
- Cierre de sesión y detección de clientes desconectados.

# Estructura
- Servidor: gestiona usuarios, entrega mensajes y revisa las conexiones cada 15 segundos.
- Cliente: presenta el menú y recibe notificaciones mediante callbacks.

# Requisitos
JDK 17 y Apache Maven.

# Ejecución
Desde la carpeta que contiene Cliente y Servidor, compila:
mvn -f Servidor/pom.xml clean compile
mvn -f Cliente/pom.xml clean compile

# Inicia el servidor:
java -cp Servidor/target/classes co.edu.unicauca.servidor.servicios.ServidorDeObjetos
# En otra terminal, inicia un cliente:
java -cp Cliente/target/classes co.edu.unicauca.cliente.servicios.ClienteDeObjetos
Repite el comando del cliente en otra terminal para conectar más usuarios con nicknames diferentes.

# Configuración
Cada módulo incluye src/main/resources/configuracion.properties. La conexión local utiliza 127.0.0.1, puerto 1099 y el objeto ServidorChat. Si modificas la configuración, vuelve a compilar y reinicia el módulo.

## Autores
- Carlo Daza
- Julian Narvaez
- Jhoiner Puentes
- Santiago Gutierrez
- Brayan Charry
