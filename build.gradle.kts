plugins {
    kotlin("jvm") version "2.1.10"
}

group = "org.example"
version = "2.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.seleniumhq.selenium:selenium-chrome-driver:4.29.0")
    implementation("org.seleniumhq.selenium:selenium-support:4.29.0")
    implementation("org.seleniumhq.selenium:selenium-devtools-v118:4.16.1")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest.attributes("Main-Class" to "org.example.MainKt")
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

kotlin {
    jvmToolchain(18)
}