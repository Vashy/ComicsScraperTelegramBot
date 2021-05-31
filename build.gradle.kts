import org.gradle.api.tasks.testing.logging.TestExceptionFormat

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
    implementation(group = "org.jsoup", name = "jsoup", version = "1.13.1")
    implementation(group = "io.github.kotlin-telegram-bot.kotlin-telegram-bot", name = "telegram", version = "6.0.4")

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = "5.4.2")
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-params", version = "5.4.2")
    testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = "5.4.2")
}

application {
    mainClass.set("it.vashykator.scraper.MainKt")
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty("webTest", System.getProperty("webTest"))
    with(testLogging) {
        showStandardStreams = true
        exceptionFormat = TestExceptionFormat.FULL
        events("passed", "skipped", "failed", "standardOut", "standardError")
    }
}
