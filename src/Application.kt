package com.matthewcasperson

import com.auth0.jwk.JwkProviderBuilder
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.concurrent.TimeUnit

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun validateCreds(credential: JWTCredential): JWTPrincipal? {
    val containsAudience = credential.payload.audience.contains(System.getenv("AUDIENCE"))

    if (containsAudience) {
        return JWTPrincipal(credential.payload)
    }

    return null
}

fun Application.module() {

    val jwkProvider = JwkProviderBuilder(System.getenv("ISSUER"))
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()

    install(Authentication) {
        jwt("auth0") {
            verifier(jwkProvider, System.getenv("ISSUER"))
            validate { credential -> validateCreds(credential) }
        }
    }
    install(CORS) {
        anyHost()
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        allowCredentials = true
        allowNonSimpleContentTypes = true

    }

    routing {
        get("/api/messages/public") {
            call.respondText(
                """{"message": "The API doesn't require an access token to share this message."}""",
                contentType = ContentType.Application.Json
            )
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
        authenticate("auth0") {
            get("/api/messages/admin") {
                call.respondText(
                    """{"message": "The API successfully recognized you as an admin."}""",
                    contentType = ContentType.Application.Json
                )
            }
        }
    }
}

