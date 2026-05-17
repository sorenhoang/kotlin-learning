plugins {
    // Lets Gradle auto-download a matching JDK if none is installed.
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "design-patterns"

// Add more subprojects here as you build them by hand.
include(":01-creational:01-singleton")
include(":01-creational:02-factory-method")
