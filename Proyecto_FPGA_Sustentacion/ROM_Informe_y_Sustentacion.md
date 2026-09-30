# Mi parte del laboratorio: memoria ROM

Este material corresponde al proyecto `Proyecto_FPGA_Sustentacion_INTEGRADO.zip` y al requisito de generar e inicializar la ROM que guarda el código de operación y el dato de entrada. Está pensado para pasar al informe **realizado a mano** que pide la guía y para preparar la sustentación individual. Los resultados de la tabla son valores esperados calculados a partir del circuito; deben contrastarse con la simulación funcional.

## Texto para el informe

La ROM almacena el pequeño programa que ejecuta el procesador. Tiene **8 posiciones de 11 bits**, por lo que necesita **3 bits de dirección** (`2³ = 8`) y guarda **88 bits** en total. El contador de rizo genera la dirección `PC[2..0]`. Cada posición contiene una instrucción con este formato:

```text
       10   9   8 | 7 6 5 4 3 2 1 0
       M    S1  S0|        B[7..0]
       └─ opcode ─┘|      operando B
```

La salida de la ROM es `F[10..0]`. Los bits `F[10]`, `F[9]` y `F[8]` controlan la operación de la ALU. Los bits `F[7..0]` son el operando B. El otro operando, A, procede de `ACC[7..0]`, el resultado guardado en el registro acumulador. Por eso cada operación utiliza como A el resultado de la operación anterior. Después del reset, ACC inicia en `00` hexadecimal.

El archivo que inicializa esta memoria es `PROYECTO_INTEGRADO/miROM.mif`. Allí se declaran `WIDTH=11`, `DEPTH=8`, direcciones decimales y datos hexadecimales. El módulo `miROM.vhd` utiliza `init_file => "miROM.mif"`; el proyecto también incorpora ese MIF en su configuración. Cada dato se escribe con **tres cifras hexadecimales**: la primera es el código de operación (`0` a `7`) y las dos últimas expresan B (`00` a `FF`). Por ejemplo, `501` significa **sumar B=01 al acumulador**; el `5` identifica la suma y no es un valor de A.

El contador recorre las direcciones en orden **0 → 1 → 2 → 3 → 4 → 5 → 6 → 7**. Así, cada instrucción está en la dirección correspondiente a su código de operación. Durante la ejecución, la ALU reciba sucesivamente los códigos `000`, `001`, `010`, `011`, `100`, `101`, `110` y `111`: las ocho operaciones requeridas.

En este montaje, el acumulador y el contador avanzan con el flanco ascendente de `PULSO`, producido por el antirrebote del botón. La ROM toma la dirección siguiente cuando se suelta el botón, mediante el reloj `CLK_ROM`, que invierte `PULSO`. Ese desfase permite que el valor leído de la ROM esté disponible para la siguiente pulsación. Al observar la simulación, el PC puede indicar ya la dirección siguiente mientras ACC acaba de guardar el resultado de la instrucción previa.

## Tabla del programa original, en orden de ejecución

Todos los valores de la tabla están en **hexadecimal**, salvo la dirección y el número de paso.

| Paso | PC / dirección | Palabra ROM | Código `M S1 S0` | B | Operación con A=ACC anterior | ACC esperado |
|---:|---:|---:|:---:|:---:|---|:---:|
| Reset | — | — | — | — | Inicializar acumulador | `00` |
| 1 | 0 | `000` | `000` | `00` | Complemento bit a bit de `00` | `FF` |
| 2 | 1 | `10F` | `001` | `0F` | `FF AND 0F` | `0F` |
| 3 | 2 | `200` | `010` | `00` | Transferir `0F` | `0F` |
| 4 | 3 | `3F0` | `011` | `F0` | `0F OR F0` | `FF` |
| 5 | 4 | `400` | `100` | `00` | `FF − 1` | `FE` |
| 6 | 5 | `501` | `101` | `01` | `FE + 01` | `FF` |
| 7 | 6 | `601` | `110` | `01` | `FF − 01` | `FE` |
| 8 | 7 | `700` | `111` | `00` | `FE + 1` | `FF` |

La operación de transferencia deja ACC igual, lo que no significa que la ROM se haya detenido: sí se leyó y ejecutó su instrucción. Las operaciones de complemento, transferencia, decremento e incremento no utilizan B para calcular el resultado.

### Contenido físico de `miROM.mif`

Al leer el archivo de direcciones 0 a 7, el orden coincide con la tabla de ejecución:

```text
WIDTH=11;
DEPTH=8;
ADDRESS_RADIX=UNS;
DATA_RADIX=HEX;
CONTENT BEGIN
    0 : 000;
    1 : 10F;
    2 : 200;
    3 : 3F0;
    4 : 400;
    5 : 501;
    6 : 601;
    7 : 700;
END;
```

## Guion breve para decirlo en la sustentación

> «Mi parte es la ROM del procesador. La configuré con ocho posiciones de once bits: tres bits son el código de operación y ocho bits son el dato B. El contador de programa tiene tres bits y entrega la dirección. Como el contador ahora cuenta de 0 a 7 en orden ascendente, cada instrucción está en la dirección correspondiente al código de operación de la ALU. La ROM entrega `M`, `S1`, `S0` y B; A viene del acumulador, que guarda el resultado anterior. El archivo `miROM.mif` contiene las instrucciones originales. Por ejemplo, en la dirección 5 la palabra `501` indica suma con B igual a 1. Tras cambiar un valor de B, debo compilar y ejecutar una simulación nueva. Para volver al programa inicial, restauro el MIF original y recompilo.»

Al mostrar el proyecto, abrir `PROYECTO_INTEGRADO/miROM.mif` y señalar la línea `5 : 501;`, los parámetros `WIDTH=11` y `DEPTH=8`, y luego observar `ROM_OUT`, `PULSO`, `PC` y `ACC` en `SIMULACION_COMPLETA.vwf`. La correspondencia de bits también se ve en `PROYECTO_TOP.bdf`.

## Si el profesor pide valores nuevos

El ZIP entregado contiene `CONFIGURAR_ENTRADAS.cmd`. Para **mantener las ocho operaciones** y cambiar sus datos B, elegir la opción **1** e ingresar los cuatro operandos que sí se usan: AND, OR, suma y resta. El menú actualiza `PROYECTO_INTEGRADO/miROM.mif` y genera `CONFIGURACION_ACTUAL.txt` con una traza esperada. Después, compilar en Quartus y volver a ejecutar `SIMULACION_COMPLETA.vwf`. La ROM no se actualiza en una simulación o una placa ya cargada solo por editar el archivo.

Ejemplo sencillo: si en el paso de suma se pide B=7 decimal y se conserva el resto del programa original, la dirección 5 pasa de `501` a **`507`**. Antes de esa operación ACC=`FE`; entonces `FE + 07 = 105` hexadecimal, y la salida de 8 bits conserva los dos últimos dígitos: **`05`**. El resto del programa también continúa desde ese nuevo resultado: después de restar `01` se obtiene `04` y después de incrementar se obtiene `05`.

Si pide **A y B independientes para una operación**, elegir la opción **2**. En la primera instrucción se carga A porque, partiendo de ACC=`00`, se cumple `00 OR A = A`; en la segunda instrucción se ejecuta la operación con B. Por ejemplo, A=12 y B=7 en suma: primera instrucción `30C` en dirección 0, segunda `507` en dirección 1. ACC pasa de `00` a `0C` (12 decimal) y luego a **`13`** (19 decimal). Este modo sustituye temporalmente el programa de ocho operaciones. Para recuperar las instrucciones originales, usar la opción **3** y compilar y simular de nuevo. **Reset solo reinicia ACC y PC; no restaura el MIF.**

Si la demostración es en la placa, el nuevo MIF debe compilarse y el `.sof` recién generado debe cargarse con el Programmer. En la placa, `SIM_MODE/SW0` debe estar en 0; en el VWF de simulación permanece en 1 para acelerar el antirrebote.

## Preguntas probables y respuestas cortas

**¿Por qué son 11 bits por palabra?** Porque hay 3 bits para seleccionar una de 8 operaciones y 8 bits para B: `3 + 8 = 11`.

**¿Por qué son 3 bits de dirección?** Tres bits permiten `2³ = 8` direcciones, de 0 a 7.

**¿Qué significa `10F`?** El primer dígito, `1`, corresponde al opcode binario `001`, AND. `0F` es B. No significa una cantidad decimal de 271.

**¿Por qué el archivo está en ese orden?** Porque el PC recorre `0,1,2,3,4,5,6,7`. Cada dirección contiene la operación original del mismo número. En la prueba A/B independiente, la dirección 0 carga A y la 1 ejecuta la operación elegida.

**¿De dónde sale A?** De `ACC[7..0]`, el registro que almacena el resultado anterior. En el proyecto integrado no se escribe A directamente en el VWF.

**¿Cómo se resta si la ROM solo guarda un opcode y B?** El opcode de resta es `110`; el circuito conecta `Ci=1` automáticamente para esa operación. La ALU realiza la resta de 8 bits usando B complementado y el acarreo de entrada.

**¿Basta con cambiar el valor en el VWF?** No. B se almacena en la ROM, por lo que se modifica `miROM.mif`, se recompila y se ejecuta una simulación nueva. El VWF aporta reloj, reset y pulsaciones del botón.

**¿Qué hacen las X de las salidas en el VWF?** Evitan comparaciones contra ceros esperados de una simulación antigua. Las entradas siguen definidas y la simulación calcula las salidas reales.

## Comprobación y alcance

Los valores, conexiones y orden de ejecución anteriores se verificaron en `miROM.mif`, `miROM.vhd`, `PROYECTO_TOP.bdf` y `CONFIGURACION_ACTUAL.txt` del ZIP entregado. La secuencia se comprobó mediante simulación funcional en Quartus II 13.0sp1: PC avanzó 0 a 7 y ACC tomó FF,0F,0F,FF,FE,FF,FE,FF. También se simuló la prueba A=FA, B=B9, suma: ACC=FA tras cargar A y ACC=B3 tras sumar, con Cout=1 durante la suma.
