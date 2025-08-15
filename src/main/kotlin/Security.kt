package id.neotica

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import id.neotica.utils.baseUrl
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt

fun Application.configureSecurity() {
    authentication {

        jwt("auth-jwt") {
            realm = "access notes"
            verifier(
                JWT
                    .require(Algorithm.HMAC256("lol"))
                    .withIssuer("${baseUrl}/")
                    .build()
            )
            validate {
                if (it.payload.getClaim("id").asString().isNotEmpty()) {
                    JWTPrincipal(it.payload)
                } else {
                    println("✨ jwt auth failed.")
                }
            }
        }

        jwt("refresh-jwt") {
            realm = "access notes refresh"
            verifier(
                JWT
                    .require(Algorithm.HMAC256("lol"))
                    .withIssuer("${baseUrl}/")
                    .build()
            )
            validate {
                if (it.payload.getClaim("refreshId").asString().isNotEmpty()) {
                    JWTPrincipal(it.payload)
                } else {
                    println("✨ jwt auth failed.")
                }
            }
        }
    }
}
