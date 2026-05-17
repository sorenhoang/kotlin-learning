plugins {
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1" apply false
    kotlin("jvm") version "2.3.21"
}

allprojects {
    group = "com.kotlinlearning.designpatterns"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "application")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")


    dependencies {
        "implementation"(kotlin("stdlib"))
        "testImplementation"(kotlin("test"))
    }

    // Force both Java and Kotlin to compile against the same JVM (17)
    // via a Java toolchain — Gradle will download JDK 17 if needed.
    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}

// Root-level alias task: format toàn bộ Kotlin code trong mọi subproject.
// Chạy: ./gradlew ktFormat
tasks.register("ktFormat") {
    group = "formatting"
    description = "Format all Kotlin code across every subproject"
    dependsOn(subprojects.map { "${it.path}:ktlintFormat" })
}

// Tuỳ chọn: chỉ check không format. Hữu ích trong CI.
// Chạy: ./gradlew ktCheck
tasks.register("ktCheck") {
    group = "verification"
    description = "Check Kotlin code style across every subproject (no auto-fix)"
    dependsOn(subprojects.map { "${it.path}:ktlintCheck" })
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}
repositories {
    mavenCentral()
}