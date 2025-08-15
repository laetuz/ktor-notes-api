package id.neotica.presentation

import id.neotica.data.repository.AuthRepositoryImpl
import id.neotica.domain.NeoUser
import io.ktor.http.*
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class AuthRoute(private val authRepo: AuthRepositoryImpl) {
    fun invoke(route: Route) {
        route.apply {
            login()
            authenticate("refresh-jwt") {
                reLogin()
            }
            register()
            delete()
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

    private fun Route.reLogin() {
        get("/reload") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("refreshId", String::class) ?: return@get call.respond(HttpStatusCode.Unauthorized)

            call.respond(authRepo.refreshLogin(userId))
        }
    }

    private fun Route.register() {
        post {
            val user = call.receive<NeoUser>()
            call.respond(authRepo.register(user))
        }
    }

    private fun Route.delete() {
        delete {
            val id = call.request.header("Id")
                ?: return@delete call.respondText("Missing 'Id' header", status = HttpStatusCode.BadRequest)
            call.respond(authRepo.deleteUser(id))
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