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

### Steps

1. Draft a minimal design of the solution

2. Migrate the code to `Scala 3`

3. Run tests to make sure everything works

4. Add `Akka Streams` library for stream processing
