val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val h2_version: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.3.9"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
}

group = "com.moboinfo"
version = "0.0.1"

application {
    mainClass.set("com.moboinfo.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-caching-headers-jvm")
    implementation("io.ktor:ktor-server-openapi")
    implementation("io.ktor:ktor-server-swagger-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-serialization-gson-jvm")
    implementation("io.ktor:ktor-serialization-jackson-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")


    implementation ("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation ("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation ("org.jetbrains.exposed:exposed-dao:$exposed_version")

    implementation ("com.h2database:h2:$h2_version")
    implementation ("com.zaxxer:HikariCP:3.4.2")
    implementation("mysql:mysql-connector-java:8.0.27")

    // ktorm
    implementation ("org.ktorm:ktorm-core:3.5.0")
    implementation ("org.ktorm:ktorm-support-mysql:3.5.0")
    implementation("io.ktor:ktor-server-freemarker:$ktor_version")


}
