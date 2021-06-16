package com.matthewcasperson

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

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
        get("/api/messages/protected") {
            call.respondText(
                """{"message": "The API successfully validated your access token."}""",
                contentType = ContentType.Application.Json
            )
        }
    }

    routing {
        get("/api/messages/admin") {
            call.respondText(
                """{"message": "The API successfully recognized you as an admin."}""",
                contentType = ContentType.Application.Json
            )
        }
    }
}

