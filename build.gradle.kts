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
}

application {
    mainClass.set("it.vashykator.scraper.MainKt")
}
