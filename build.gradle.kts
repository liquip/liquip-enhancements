plugins {
    java
}

group = "io.github.liquip"
version = "1.0.0-pre"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT")
    compileOnly("io.github.liquip:api:3.1.0") {
        version { branch = "nightly" }
    }
    compileOnly("io.github.liquip:paper-core:3.1.0") {
        version { branch = "nightly" }
    }
}

tasks.processResources {
    filesMatching("**/paper-plugin.yml") {
        filter {
            return@filter it.replace("\${version}", project.version.toString())
        }
    }
}