# Kotlin Design Patterns

A workspace for learning Design Patterns the Kotlin way. Each pattern is a Gradle subproject.

## Current structure

```
design-patterns/
├── settings.gradle.kts        # lists all subprojects (currently: singleton only)
├── build.gradle.kts           # shared Kotlin/JVM config for all subprojects
├── gradle.properties
├── .gitignore
└── 01-creational/
    └── 01-singleton/          # the only pre-made example
        ├── README.md
        ├── build.gradle.kts
        ├── src/main/kotlin/
        └── src/test/kotlin/
```

## Adding more patterns by hand

For each new pattern:

1. Create the folder under the right category, e.g.
   `02-structural/04-decorator/`
2. Add a minimal `build.gradle.kts`:
   ```kotlin
   plugins {
       kotlin("jvm")
       application
   }
   application {
       mainClass.set("DecoratorKt") // PascalCase name + "Kt"
   }
   ```
3. Create `src/main/kotlin/Decorator.kt` with `fun main() { ... }`.
4. Add `README.md` describing the pattern.
5. Register it in `settings.gradle.kts`:
   ```kotlin
   include(":02-structural:04-decorator")
   ```

## Running

```bash
./gradlew :01-creational:01-singleton:run
```
