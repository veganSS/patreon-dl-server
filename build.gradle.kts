import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.4.32"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    kotlin("plugin.serialization") version "1.4.30"
}

group = "dev.kdrag0n"
version = "0.0.1"

repositories {
    mavenCentral()
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "13"
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-locations:$ktor_version")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-client-apache:$ktor_version")
    implementation("io.ktor:ktor-server-sessions:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.apache.commons:commons-compress:1.20")
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
    implementation("com.patreon:patreon:0.4.2") {
        exclude(group = "org.slf4j", module = "slf4j-log4j12")
    }

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
}

val nettyMainClass = "io.ktor.server.netty.EngineMain"
application {
    mainClass.set(nettyMainClass)
    // ShadowJar requires this deprecated property
    mainClassName = nettyMainClass
}