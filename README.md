# Solution

## Flaws of the legacy solution

- No Single Responsability Principle, all the code is in one place and do everything.

- It uses mutable datastructures (not good for concurrency)

- It uses the `Global ExecutionContext` (not a good idea for production ready apps)

- Imperative and side effectful code

- Uses `Scala 2.12.x` (the new legacy `Scala` version)

- Too low level

## Solution

### High level architecture

![Alt text](diagrams/architecture.png?raw=true "Architecture")

`TODO TODO TODO`

![Alt text](diagrams/follower-service.png?raw=true "Architecture")

`TODO TODO TODO`

### TODO

---- Critical

- persistence layer
- redis session manager => terminar de implementar SessionRepositoryImpl
- fix tests
- business logic
- tcp server
- plug everything
- logging

---- Bonus

- kubernetes
- unit testing
- refactor to Scala 3