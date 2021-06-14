package com.mathewceron

import com.matthewcasperson.module
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

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
