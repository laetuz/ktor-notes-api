package id.neotica

import id.neotica.data.dao.note.NoteEntity
import id.neotica.data.dao.note.NoteTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

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