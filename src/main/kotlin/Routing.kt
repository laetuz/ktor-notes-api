package id.neotica

import id.neotica.presentation.AuthRoute
import id.neotica.presentation.NoteRoute
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val noteRoutes by inject<NoteRoute>()
    val authRoutes by inject<AuthRoute>()

    routing {
        get {
            call.respond("Hello World")
        }
        route("/auth") {
            authRoutes.invoke(this)
        }
        route("/notes") {
            noteRoutes.register(this)
        }
    }
}
