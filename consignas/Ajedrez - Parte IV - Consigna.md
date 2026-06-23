# Diseño de sistemas
## TP - Parte IV - Ajedrez
2026-06-09

# TP - Parte IV - Ajedrez

## Introducción
La Comisión Sudamericana de Ajedrez y Damas quiere organizar mundiales de ambos juegos. El problema que tienen es que no pueden encontrar un lugar al que todos los jugadores puedan ir para jugar presencialmente.
Debido a esto, la comisión quiere que su juego pueda ser jugado en forma _online_, sin necesidad de que ambos jugadores jueguen en la misma computadora.

## Restricciones técnicas
* Una computadora será la base con el juego y su estado.
* El componente encargado del armado de las conexiones será provisto por la cátedra.
* **No** es necesario _hostear_ el juego en ningún servidor. Basta con que el juego se pueda jugar en _LAN_ (Local Area Network).

## Consigna
1. Integre su juego de ajedrez y damas con el componente de client-server para poder jugar al juego en _LAN_. Tenga en cuenta que:
   a. Debe haber 1 servidor y N clientes. El servidor a su vez es un cliente, y jugará siempre como blancas.
   b. El primer cliente que se conecte al servidor jugará como negras.
   c. El resto de conexiones serán _read-only_. Estas conexiones no pueden ejecutar movimientos, solamente deben poder ver los updates del tablero.
   d. Puede haber solo 1 conexión por cada jugador.
   e. No hace falta que la configuración de los servidores sea por UI, puede ser un archivo de configuración o ser parte del código. Si es parte del código, debe quedar en un lugar aislado dónde sea fácil de cambiar.
