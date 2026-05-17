# kotlin-learning

A personal repo for learning Kotlin — focusing on language fundamentals and backend development.

## Why this repo

I'm using this as a structured space to learn Kotlin by reading, writing, and experimenting. Each topic lives in its own folder with its own examples, notes, and exercises.

## What's inside

```
kotlin-learning/
└── design-patterns/        # Design patterns implemented the Kotlin way
    └── 01-creational/
        └── 01-singleton/   # Done
```

### `design-patterns/`

A Gradle multi-project workspace. Each pattern is its own subproject with:

- `README.md` — what the pattern is, when to use it, Kotlin-specific notes, common pitfalls.
- `build.gradle.kts` — minimal Kotlin/JVM + `application` plugin setup.
- `src/main/kotlin/` — the example code.
- `src/test/kotlin/` — tests (when added).

Currently complete:
- Creational
  - Singleton

Coming as I work through them:
- Creational: Factory Method, Abstract Factory, Builder, Prototype
- Structural: Adapter, Bridge, Composite, Decorator, Facade, Flyweight, Proxy
- Behavioral: Chain of Responsibility, Command, Iterator, Mediator, Memento, Observer, State, Strategy, Template Method, Visitor, Interpreter
- Kotlin-idiomatic: Delegation, Extension Functions, Scope Functions, Sealed Classes, Type-safe Builders / DSL, Result / Either, Coroutine Patterns, Inline / Value Classes

## Requirements

- **JDK 17+** (Gradle toolchain will auto-download JDK 17 if missing).
- **Gradle** is bundled via the wrapper — no separate install needed.
- **Kotlin 2.1.20** — managed by Gradle.

## How to run an example

From the `design-patterns` folder:

```bash
./gradlew :01-creational:01-singleton:run
```

General form:

```bash
./gradlew :<category>:<pattern>:run
# example:
./gradlew :02-structural:04-decorator:run
```

To run tests for a pattern:

```bash
./gradlew :01-creational:01-singleton:test
```

To stop the Gradle daemon (handy when bumping Kotlin/JDK versions):

```bash
./gradlew --stop
```

## Adding a new pattern

1. Create the folder under the right category, e.g.
   `design-patterns/02-structural/04-decorator/`
2. Add `build.gradle.kts`:
   ```kotlin
   plugins {
       kotlin("jvm")
       application
   }
   application {
       mainClass.set("structural.decorator.DecoratorKt")
   }
   ```
3. Create `src/main/kotlin/Decorator.kt` with `package structural.decorator` and `fun main() { ... }`.
4. Add a `README.md` next to `build.gradle.kts`.
5. Register the subproject in `design-patterns/settings.gradle.kts`:
   ```kotlin
   include(":02-structural:04-decorator")
   ```

## Conventions

- **Folder names**: kebab-case with a numeric prefix for ordering (`04-decorator`).
- **Kotlin files**: PascalCase matching the pattern name (`Decorator.kt`).
- **Package names**: lower-dot-case (`structural.decorator`).
- **`mainClass`**: must include the full package path + the file name suffixed with `Kt` (`structural.decorator.DecoratorKt`).

## License

Personal learning material — no license intended.
