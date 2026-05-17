# Singleton

## Intent
Ensure a class has only one instance, and provide a global point of access to it.

## Problem it solves
Some things should only exist once: a connection pool, a configuration registry, an in-memory cache, a logger. Creating multiple instances would waste resources or cause inconsistent state.

## Kotlin-specific notes
Kotlin has **first-class support** for Singleton via the `object` keyword. There is no need for private constructors, double-checked locking, or `getInstance()` boilerplate.

```kotlin
object AppConfig {
    val apiUrl: String = System.getenv("API_URL") ?: "http://localhost:8080"
    fun reload() { /* ... */ }
}

// Usage
AppConfig.apiUrl
```

- Thread-safe by default (initialized lazily on first access).
- Cannot have a constructor (use `init {}` blocks if needed).
- Can implement interfaces and extend classes.
- For Singletons inside a class, use `companion object`.

## When to use
- Stateless service holders (loggers, formatters)
- Configuration / environment objects
- Application-wide caches or registries
- Coordinating access to a shared resource

## When NOT to use
- Anywhere you'd want to mock or swap implementations for tests → prefer dependency injection.
- Whenever the "global" state could be passed as a parameter instead.
- Multi-tenant servers where each tenant needs its own instance.

## Common pitfalls
- Singletons make testing harder because they're implicit dependencies.
- They can hide tight coupling — overuse becomes "global variables in disguise".
- Be careful about mutable state inside an `object` — concurrent access needs synchronization.

## Run
```bash
./gradlew :01-creational:01-singleton:run
```

## TODO
- [ ] Implement basic `object` singleton
- [ ] Implement a thread-safe lazy singleton with state
- [ ] Compare with `companion object` factory
