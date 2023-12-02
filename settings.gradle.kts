rootProject.name = "liquip-enhancements"

sourceControl {
    gitRepository(uri("https://github.com/liquip/liquip-plugin.git")) {
        producesModule("io.github.liquip:api")
        producesModule("io.github.liquip:paper-core")
    }
}
