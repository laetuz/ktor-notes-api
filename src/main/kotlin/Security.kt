package id.neotica

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt

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

        jwt("auth-jwt") {
            realm = "access notes"
            verifier(
                JWT
                    .require(Algorithm.HMAC256("lol"))
                    .withAudience("http://127.0.0.1:8081/notes")
                    .withIssuer("http://127.0.0.1:8081/")
                    .build()
            )
            validate {
                if (it.payload.getClaim("username").asString().isNotEmpty()) {
                    JWTPrincipal(it.payload)
                } else {
                    println("✨ jwt auth failed.")
                }
            }
        }
    }
}
