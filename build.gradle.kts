import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.22"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "io.github.liquip"
version = "0.1.0-alpha"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven {
        url = uri("https://maven.pkg.github.com/liquip/liquip-plugin")
        credentials {
            username = System.getenv("GRADLE_GITHUB_USERNAME")
            password = System.getenv("GRADLE_GITHUB_TOKEN")
        }
    }
    maven {
        url = uri("https://maven.pkg.github.com/sqyyy-jar/cougar-ui")
        credentials {
            username = System.getenv("GRADLE_GITHUB_USERNAME")
            password = System.getenv("GRADLE_GITHUB_TOKEN")
        }
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly("io.github.liquip:api:2.0.0-beta")
    compileOnly("io.github.liquip:paper-core:2.0.0-beta")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}