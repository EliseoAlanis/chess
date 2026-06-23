# Diseño de sistemas
## TP - Parte III - Ajedrez
**2026-06-02**

# TP - Parte III - Ajedrez

## Introducción
Momentos después de terminar y entregar el juego de ajedrez, usted conoce la última noticia: la Comisión Sudamericana de Ajedrez se fusionó con la Comisión Sudamericana de Damas para formar la Comisión Sudamericana de Ajedrez y Damas. Por este motivo, la comisión quiere que junto al ajedrez, también se lance al mercado un juego de damas con un look-and-feel parecido al del ajedrez. Las mismas restricciones que aplican al ajedrez tienen que aplicar al juego de damas.

Aparte de esta nueva modalidad, a modo de mejora de la versión actual, se pide poder volver atrás con jugadas realizadas en la aplicación. Esto cumple 2 objetivos:

- Poder ver los movimientos que llevaron a cierto momento, y volver al movimiento original.
- Cuando usado en prácticas, tener la capacidad de hacer un movimiento para poder corregir a los aprendices.

## Restricciones técnicas
- Deben tener 3 paquetes de Java: uno para el “chess”, otro para el “checkers” y otro dónde están los componentes comunes de cada juego.
- Los componentes de “chess” y de “checkers” no deben depender entre sí, deben depender solamente del componente común.

## Consigna
1. Implemente el juego de damas (checkers) con las restricciones impuestas
2. Implementar la funcionalidad de undo/redo que fue incorporada a la UI usada en el chess.
