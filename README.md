# Solution

## Flaws of the legacy solution

- No Single Responsability Principle, all the code is in one place and do everything.

- It uses mutable datastructures (not good for concurrency)

- It uses the `Global ExecutionContext` (not a good idea for production ready apps)

- Imperative and side effectful code

- Uses `Scala 2.12.x` (the new legacy `Scala` version)

- Too low level

## Approach

### Pre steps

0. Run the test to see everything is OK

1. Understand de original code

## Improvements

- Retryable consumer on consumer side

#########################

Detalle

Se plantea una arquitectura Event Sourcing, separando comandos, de eventos de estado.

Se definieron dichos comandos con sus correspondientes unit tests siguiendo un approach TDD. Dichos componentes fueron definidos en la capa de domain

########################

TODO

- organizar build sbt: buscar una forma que las dependencias de event producer y notification esten mejor organizadas para no repertirse
- diagramas en readme
- agregar unit test
- implementar segundo caso de uso