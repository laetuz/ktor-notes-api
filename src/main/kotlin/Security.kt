package id.neotica

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureSecurity() {
    authentication {
        basic(name = "auth-basic") {
            realm = "Access to the '/' path"
            validate { credentials ->
                val jetbrainsPasswordHash = BCrypt.hashpw("foobar", BCrypt.gensalt())
                println("🧏🏻 $jetbrainsPasswordHash")
                if (BCrypt.checkpw(credentials.password, jetbrainsPasswordHash)) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
}
