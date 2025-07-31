package id.neotica.presentation

import id.neotica.data.repository.AuthRepositoryImpl
import id.neotica.domain.NeoUser
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class AuthRoute(private val authRepo: AuthRepositoryImpl) {
    fun invoke(route: Route) {
        route.apply {
            login()
            register()
            getAllUser()
            checkUser()
        }
    }

    private fun Route.login() {
        get {
            val username = call.request.header("Username")
                ?: return@get call.respondText("Missing 'username' header", status = HttpStatusCode.BadRequest)
            val password = call.request.header("Password")
                ?: return@get call.respondText("Missing 'password' header", status = HttpStatusCode.BadRequest)
            call.respond(authRepo.login(username, password))
        }
    }

    private fun Route.register() {
        post {
            val user = call.receive<NeoUser>()
            call.respond(authRepo.register(user))
        }
    }

    private fun Route.getAllUser() {
        get("/users") { call.respond(authRepo.getAllUser()) }
    }
    private fun Route.checkUser() {
        get("/check") {
            val username = call.request.header("Username")
                ?: return@get call.respondText("Missing 'username' header", status = HttpStatusCode.BadRequest)
            call.respond(authRepo.checkUser(username))
        }
    }
}