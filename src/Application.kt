package com.mathewceron

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module() {
    val verifier = JWT
        .require(Algorithm.RSA256(readPublicKey(javaClass.getResourceAsStream("/auth0.pem")), null))
        .withAudience(System.getenv("AUDIENCE"))
        .withIssuer(System.getenv("ISSUER"))
        .build()

    install(Authentication) {
        jwt("auth0") {
            verifier(verifier)
            validate { credential ->  JWTPrincipal(credential.payload)}
        }
        jwt("auth0-admin") {
            verifier(verifier)
            validate { credential ->
                if (credential.payload.claims["scopes"]?.asString()?.split(" ")?.contains("read:admin-messages") == true) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }

    routing {
        get("/api/messages/public") {
            call.respondText(
                """{"message": "The API doesn't require an access token to share this message."}""",
                contentType = ContentType.Application.Json)
        }
    }

    routing {
        authenticate("auth0") {
            get("/api/messages/protected") {
                call.respondText(
                    """{"message": "The API successfully validated your access token."}""",
                    contentType = ContentType.Application.Json
                )
            }
        }
    }

    routing {
        authenticate("auth0-admin") {
            get("/api/messages/admin") {
                call.respondText(
                    """{"message": "The API successfully recognized you as an admin."}""",
                    contentType = ContentType.Application.Json
                )
            }
        }
    }
}

