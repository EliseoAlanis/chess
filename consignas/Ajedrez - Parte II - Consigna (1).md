# Diseño de sistemas
## TP - Ajedrez - Parte II
**2026-05-19**

# TP - Ajedrez - Parte II

## Introducción
Ahora que ya dispone de un diseño preliminar del motor del juego de ajedrez es momento de ponerlo a prueba. Se ha decidido sobre el total de alternativas del ajedrez que se soportan, inicialmente, las siguientes:

- Ajedrez aleatorio de Fischer
- Ajedrez desplazado
- Ajedrez transcendental
- Ajedrez jubilado
- Ajedrez mariposa
- Ajedrez del constructor
- Ajedrez extinción
- Ajedrez 3 jaques
- Ajedrez de los caballeros Jedi
- Ajedrez progresivo
- Ajedrez monstruoso
- Ajedrez del Rey Batallador
- Ajedrez berlinés (Aqui esta explicado el peor https://es.wikipedia.org/wiki/Peón_de_Berolina)
- Ajedrez de Capablanca
- Ajedrez Aleatorio de Capablanca
- Ajedrez grandioso

Sobre los mismos se puede leer en este articulo:
https://es.wikipedia.org/wiki/Variante_del_ajedrez

## Consigna
1. Ponga a prueba su diseño al tratar de ver cómo se implementarán estas variantes en el mismo.
2. Modifique su diseño e implementación lo necesario para poder soportar las variantes. Esto no implica tener implementadas todas las variantes, pero sí alguna representativa de cada “flexibilidad” que se trabajó en el diseño (Ej: Sistema de turnos modificable para el “Ajedrez progresivo”).

## Criterios mínimos esperados
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
