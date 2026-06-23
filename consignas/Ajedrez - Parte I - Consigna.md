# Diseño de sistemas
## TP3 - Parte I - Ajedrez
**2026-05-12**

# TP1 - Ajedrez - Parte I

## Introducción
La Comisión Sudamericana de Ajedrez le ha encargado trabajar en el motor, a modo de prueba de concepto, para su próximo juego de ajedrez.
Los requerimientos principales de la comisión para la prueba de concepto son:
- Con la prueba de concepto, 2 jugadores deben ser capaces de jugar un juego completo de ajedrez con sus reglas principales: el movimiento de cada pieza, los límites del tablero, los turnos de cada jugador, el jaque mate y el enroque. No tome en cuenta límites de tiempo.
- La comisión está entusiasmada para que, con esta prueba de concepto, los jugadores puedan jugar distintos tipos de ajedrez. Por ejemplo: nuevas piezas, variantes en los movimientos de las piezas existentes, condiciones de victoria distintas, nuevas posiciones iniciales para las piezas o otros tamaños de tablero.

Por ser una prueba de concepto, a la comisión no le interesa ningún tipo de interfaz gráfica.
Como referencia aqui se encuentran las reglas del ajedrez:
https://es.wikipedia.org/wiki/Leyes_del_ajedrez

## Consigna
1. En un diagrama de clases UML diseñe la prueba de concepto del juego de ajedrez.
2. Diagrama de secuencia del movimiento de 2 piezas.
3. Una primera implementación del motor que al menos soporte por lo menos 3 piezas.

## Criterios mínimos
1. **Inmutabilidad por defecto**. Si algo no es inmutable, tiene que haber una razón clara.
2. **Funciones puras**, donde sea posible.
3. **Respeto por los principios SOLID**.
4. **Tipos de retorno expresivos**.
5. **Composición sobre herencia**.
6. **Value objects** y evitar la “Primitive Obsession”.
7. **Buenas prácticas generales:**
    - Evitar el uso de `null` en Java (excepto si una librería lo requiere explícitamente).
    - No escribir métodos de más de **10 líneas**.
    - No más de **2 niveles de anidación**.
    - Nombres **claros, expresivos y específicos**.
    - Cobertura alta de tests.

## Consejos
- Piense las entidades que son parte del dominio del problema y grafique las relaciones entre ellos. Por ejemplo: **peón**, **tablero**, **pieza**, el **tablero** tiene **piezas**, etc.
- Analizar qué tipo de operaciones se aplican sobre el modelo de datos y donde conviene que se encuentren definidas.
- No busque el modelo perfecto en el primer intento. Construya un modelo y pruebe si soporta todas las funcionalidades que se le piden.
- Plantee casos concretos para probar su modelo. Por ejemplo: qué pasa si intento mover un peón 3 casilleros en vez de uno, que pasa sí el rey está en jaque, etc. ¿Es fácil agregar casos distintos? ¿Es fácil partir de un estado determinado del juego para probar un caso?
- Cuando ya diseñó el dominio que soporta las reglas del ajedrez convencional, vea como su diseño responde ante variantes en las reglas. ¿Su modelo es flexible ante cambios en el juego?
- Evite las clases “mágicas”, piense en detalle en las cosas que hace cada clase, en los inputs que necesita y en los outputs que genera.
