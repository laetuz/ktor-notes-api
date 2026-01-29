plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
}

group = "id.neotica"
version = "0.0.1"

sourceSets {
    main {
        java.srcDirs("../src/main/java")
        kotlin.srcDirs("../src/main/kotlin")
        resources.srcDirs("../src/main/resources")
    }
    test {
        kotlin.srcDirs("../src/test/kotlin")
        resources.srcDirs("../src/test/resources")
    }
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

room {
    schemaDirectory("$projectDir/schemas")
    generateKotlin = true
}

ksp {
    arg("KOIN_DEFAULT_MODULE", "true")
}

dependencies {
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.jwt)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
//    implementation(libs.exposed.sql)
    implementation(libs.exposed.migration)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.r2dbc)
    implementation(libs.h2)
    implementation(libs.postgresql)
    implementation(libs.hikari.core)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)

    implementation(libs.androidx.room.runtime.jvm)
    ksp(libs.androidx.room.compiler)
    implementation(libs.sqlite.bundled)

    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
