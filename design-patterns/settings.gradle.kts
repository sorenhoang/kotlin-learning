plugins {
    // Lets Gradle auto-download a matching JDK if none is installed.
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "design-patterns"

// Add more subprojects here as you build them by hand.
include(":01-creational:01-singleton")
include(":01-creational:02-factory-method")
include(":01-creational:03-abstract-factory")
include(":01-creational:04-builder")
include(":01-creational:05-prototype")

// Structural
include(":02-structural:01-adapter")
include(":02-structural:02-bridge")
include(":02-structural:03-composite")
include(":02-structural:04-decorator")
include(":02-structural:05-facade")
include(":02-structural:06-flyweight")
include(":02-structural:07-proxy")

// Behavioral
include(":03-behavioral:01-chain-of-responsibility")
include(":03-behavioral:02-command")
include(":03-behavioral:03-iterator")
include(":03-behavioral:04-mediator")
include(":03-behavioral:05-memento")
include(":03-behavioral:06-observer")