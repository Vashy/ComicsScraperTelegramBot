import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import java.time.LocalDateTime

val jsoupVersion = "1.13.1"
val coroutinesVersion = "1.5.0"
val kotlinTegramBotVersion = "6.0.4"
val junitVersion = "5.4.2"

plugins {
    kotlin("jvm") version "1.5.10"
    application
}

group = "it.vashykator.scraper"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(group = "org.jsoup", name = "jsoup", version = jsoupVersion)
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot", "telegram", kotlinTegramBotVersion)
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version = coroutinesVersion)

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = junitVersion)
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-params", version = junitVersion)
    testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = junitVersion)
}

application {
    mainClass.set("it.vashykator.scraper.MainKt")
}

tasks.withType<Test> {
    useJUnitPlatform()

    systemProperty("webTest", System.getProperty("webTest"))

    logger.quiet("Timestamp: ${LocalDateTime.now()}")

    with(testLogging) {
        showStandardStreams = true
        exceptionFormat = FULL
        events(PASSED, SKIPPED, FAILED)
    }
}
