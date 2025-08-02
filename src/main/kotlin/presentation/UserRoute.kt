package id.neotica.presentation

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

class UserRoute {
    fun invoke(route: Route) {
        route.apply {
            authenticate("auth-jwt") {
                getUsername()
            }
        }
    }

    private fun Route.getUsername() {
        get("/username") {
            val principal = call.principal<JWTPrincipal>()
            val username = principal?.getClaim("username", String::class) ?: return@get call.respond(HttpStatusCode.Unauthorized)

            call.respond(username)
        }
    }
}