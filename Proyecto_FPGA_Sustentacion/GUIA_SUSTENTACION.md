# Proyecto integrado con entradas configurables

El paquete abre con los datos originales. Para cambiar los datos, ejecutar **`CONFIGURAR_ENTRADAS.cmd`**; el menú escribe directamente la ROM del proyecto integrado y permite volver al programa original.

## Uso durante la sustentación

1. Extraer todo el ZIP.
2. Abrir el menú `CONFIGURAR_ENTRADAS.cmd` e ingresar los datos pedidos.
3. Abrir `PROYECTO_INTEGRADO/PROYECTO_TOP.qpf` en Quartus.
4. Ejecutar **Processing → Start Compilation** y esperar que termine sin errores.
5. Abrir `PROYECTO_INTEGRADO/SIMULACION_COMPLETA.vwf`.
6. Ejecutar **Simulation → Run Functional Simulation**.
7. Leer ACC en hexadecimal. `CONFIGURACION_ACTUAL.txt`, dentro de `PROYECTO_INTEGRADO`, contiene el cálculo de lo que debe obtenerse en cada paso.

Cada cambio de datos requiere repetir la compilación y simulación. Si el MIF estaba abierto antes de usar el menú, recargarlo desde disco: una pestaña con cambios pendientes podría sobrescribir los datos nuevos al guardarse.

## Modo 1: conservar las ocho operaciones

La opción 1 pide cuatro valores B: AND, OR, suma y resta. A sigue siendo el acumulador, es decir, el resultado anterior. Las demás operaciones no usan B.

| Paso | Dirección ROM | Operación | B original |
|---|---:|---|---:|
| 1 | 0 | NOT A | No se usa |
| 2 | 1 | A AND B | 15 |
| 3 | 2 | Transferir A | No se usa |
| 4 | 3 | A OR B | 240 |
| 5 | 4 | A − 1 | No se usa |
| 6 | 5 | A + B | 1 |
| 7 | 6 | A − B | 1 |
| 8 | 7 | A + 1 | No se usa |

Por ejemplo, ingresar AND=18, OR=32, SUMA=5 y RESTA=3 produce los resultados esperados:

`FF → 12 → 12 → 32 → 31 → 36 → 33 → 34` (hexadecimal).

El primer complemento sigue dando FF porque el acumulador comienza en cero. Transferir A conserva su valor: aunque ACC no cambie, la instrucción se ejecuta.

## Modo 2: A y B independientes para una operación

La opción 2 permite elegir una operación e ingresar A y, cuando corresponda, B. La primera instrucción carga A mediante `0 OR A`; la segunda ejecuta la operación elegida. Las seis restantes mantienen el resultado.

Ejemplo: **A=12, B=7, suma**.

En el menú seleccionar **2**, ingresar A=`12`, código de operación **5** (suma), B=`7` y pulsar ENTER para guardar.

- Reset: ACC=00.
- Primera pulsación: ACC=0C, equivalente a 12 decimal.
- Segunda pulsación: ACC=13, equivalente a 19 decimal.
- Pulsaciones siguientes: ACC=13.

Este modo prueba el procesador integrado y sustituye temporalmente su programa. Se usa para una operación concreta sobre un par de datos; las ocho operaciones originales se recuperan con la opción 3.

La opción permite NOT, AND, transferencia, OR, decremento, suma, resta e incremento. NOT es complemento bit a bit, no negación aritmética. Ci se calcula automáticamente en el circuito integrado.

## Restaurar

Elegir la opción **3**. El programa restaura los bytes originales de `miROM.mif` desde `respaldo_original/miROM.mif`. Después, compilar y simular otra vez.

Los resultados originales, contando el estado de reset, son:

`00 → FF → 0F → 0F → FF → FE → FF → FE → FF`.

Reset reinicia el acumulador y la ejecución, pero no restaura los datos de la ROM. El respaldo no se sobrescribe al usar el menú.

El VWF preparado para la sustentación se mantiene al restaurar el programa, porque tiene exactamente los mismos estímulos de entrada que el original y elimina comparaciones de salidas desactualizadas. El VWF original también está guardado en `respaldo_original`, como referencia.

## Leer la simulación

El VWF está configurado para 100 µs y ocho pulsaciones. Mantener:

- SIM_MODE=1.
- CLOCK_50 con período de 20 ns.
- Reset=0 hasta 500 ns; después, 1.
- CLK con las pulsaciones ya incluidas. Es el botón activo en bajo, no el reloj de 50 MHz.

Observar el resultado después de cada flanco ascendente de PULSO. PC avanza en ese mismo flanco; la ROM toma la instrucción siguiente al soltar el botón. Por eso el PC que se ve inmediatamente después del flanco puede señalar ya la siguiente instrucción.

ACC, PC, ROM_OUT, PULSO y Cout son salidas. No se ingresan números nuevos sobre esas filas. Los valores X guardados en las salidas del VWF indican que no se comparan contra resultados viejos; la simulación calcula sus valores reales.

Si Quartus conserva una ruta anterior en las opciones del editor, abrir **Simulation → Simulation Settings** y seleccionar el `SIMULACION_COMPLETA.vwf` de la carpeta actual. La configuración del proyecto entregado utiliza rutas relativas.

## Las ocho operaciones sobre el mismo A y B

También se conserva el subproyecto **`ALU8BIT/ALU8BIT.qpf`**, donde A y B sí son entradas directas. Esta alternativa comprueba la ALU sin el acumulador del procesador.

Abrir `ALU8BIT/ALUBIT8BIT.vwf`. Guardar una copia antes de editar. Para mantener el mismo par de números en las ocho operaciones, seleccionar todo el intervalo de 0–80 ns de A y de B, y asignar los valores nuevos mediante el comando de valor arbitrario del editor. Dejar Z y Cout en X durante todo el intervalo para retirar comparaciones antiguas. Configurar Ci=0 de 0–60 ns y Ci=1 de 60–80 ns. Los controles M, S1 y S0 ya recorren las ocho operaciones en intervalos de 10 ns.

Compilar el proyecto ALU8BIT y ejecutar su simulación funcional; el resultado se llama Z. Se corrigieron las rutas antiguas de ese proyecto. Para volver al integrado, abrir nuevamente `PROYECTO_INTEGRADO/PROYECTO_TOP.qpf`.

## Formato y límites

El menú recibe enteros sin signo entre 0 y 255. `20` significa veinte decimal; `0x14` también representa veinte. Convierte los valores al formato de la ROM automáticamente.

En `miROM.mif` una palabra es `[operación][B]`: un dígito hexadecimal de operación y dos para B. Por ejemplo, `507` significa sumar B=7; no indica que A valga 5. La ROM ejecuta las direcciones 0,1,2,3,4,5,6,7.

Los resultados conservan ocho bits. 255+1 da 00; 0−1 da FF. El archivo CONFIGURACION_ACTUAL contiene valores esperados calculados, no una captura de una simulación realizada.

## Placa física

El menú modifica el programa en el computador. Para usar los datos en la FPGA, compilar, cargar el `.sof` recién generado con el Programmer habitual, poner SIM_MODE/SW0=0, aplicar reset y avanzar con el botón. Restaurar la placa requiere restaurar el programa en el menú, recompilar y cargar el nuevo `.sof`.

## Cambios incluidos

- Menú que aplica los datos directamente y restaura el programa original.
- Cálculo de los resultados esperados desde la ROM activa.
- VWF de sustentación integrado: mismas entradas, sin comparaciones contra ceros antiguos.
- Rutas relativas en los proyectos integrado, ALU y registro; referencia del FFD del registro corregida.
- Metadatos del asistente de ROM coherentes con `miROM.mif`.
- Respaldo de archivos originales y ejemplos opcionales.

Se corrigieron el contador de rizo y tres salidas del decodificador hexadecimal en BDF. Los pines y el antirrebote se conservan. No se incluyen compilaciones ni gráficas antiguas: Quartus generará los archivos correspondientes a los datos seleccionados.
