import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

plugins {
    java
    kotlin("jvm") version "1.4.32"
}

group = "com.example.minecraft"
version = "0.0.1"

repositories {
    mavenCentral()
}

tasks.withType(KotlinJvmCompile::class.java) {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.jar {
    delete("${project.buildDir.absolutePath}/libs/*.jar")
    archiveBaseName.set("Mirai")
    manifest {
        attributes(mapOf("Main-Class" to "${project.group}.MainKt"))
    }
    from(configurations.runtimeClasspath.get().map {
        if (it.isDirectory) it else zipTree(it)
    }) {
        exclude(
            "META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA",
            "META-INF/NOTICE", "META-INF/NOTICE.txt", "META-INF/LICENSE",
            "META-INF/LICENSE.txt", "META-INF/DEPENDENCIES"
        )
    }
    val sourcesMain = sourceSets.main.get()
    sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
    from(sourcesMain.output)
}

dependencies {
    api("com.google.code.gson:gson:2.7")
    api("net.mamoe:mirai-core-api:2.7.0")
    runtimeOnly("net.mamoe:mirai-core:2.7.0")
}