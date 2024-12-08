plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.7.1"
}

group = "ru.mnsvn"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.21-R0.1-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}