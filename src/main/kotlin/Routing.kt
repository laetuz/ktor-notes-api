package id.neotica

import id.neotica.presentation.AuthRoute
import id.neotica.presentation.NoteRoute
import id.neotica.presentation.PublicNoteRoute
import id.neotica.presentation.UserRoute
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val noteRoutes by inject<NoteRoute>()
    val publicNoteRoutes by inject<PublicNoteRoute>()
    val authRoutes by inject<AuthRoute>()
    val userRoutes by inject<UserRoute>()

    routing {
        get {
            call.respond("Hello World")
        }
        route("/auth") {
            authRoutes.invoke(this)
        }
        route("/notes") {
            publicNoteRoutes.register(this)
        }
        route("/user") {
            authenticate("auth-jwt") {
                userRoutes.invoke(this)
                route("/notes") {
                    noteRoutes.invoke(this)
                }
            }
        }
    }
}
