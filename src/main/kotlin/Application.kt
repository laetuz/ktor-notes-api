package id.neotica

import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)

}

fun Application.module() {
    configureSecurity()
    configureMonitoring()
    configureFrameworks()
    configureSerialization()
    configureHTTP()
    configureRouting()
}