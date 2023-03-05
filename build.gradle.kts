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
    compileOnly("io.github.liquip:api:3.0.1-pre")
    compileOnly("io.github.liquip:paper-core:3.0.1-pre")
}

tasks.processResources {
    filesMatching("**/paper-plugin.yml") {
        filter {
            return@filter it.replace("\${version}", project.version.toString())
        }
    }
}