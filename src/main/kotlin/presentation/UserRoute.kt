package id.neotica.presentation

import id.neotica.data.repository.UserRepositoryImpl
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

class UserRoute(
    val userRepo: UserRepositoryImpl,
) {
    fun invoke(route: Route) {
        route.apply {
            getUserId()
            getUsername()
        }
    }

    private fun Route.getUserId() {
        get("/userid") {
            val principal = call.principal<JWTPrincipal>()
            val id = principal?.getClaim("id", String::class) ?: return@get call.respond(HttpStatusCode.Unauthorized)

            call.respond(id)
        }
    }

    private fun Route.getUsername() {
        get("/username") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("id", String::class) ?: return@get call.respond(HttpStatusCode.Unauthorized)

            val respond = userRepo.checkUsername(userId)
            call.respond(respond)
        }
    }
}