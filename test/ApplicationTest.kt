package com.mathewceron

import com.matthewcasperson.module
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import kotlin.test.*
import io.ktor.server.testing.*

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/api/messages/public").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}
