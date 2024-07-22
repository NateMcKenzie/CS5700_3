package instructionStreams

import ObserverTestHelper
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertEquals

class HttpServerTest {
    companion object {
        private var port = 8001
    }

    private val server = HttpServer(port++)
    private val client = HttpClient(CIO)

    init {
        server.start()
    }

    @Test
    fun getTest() = runBlocking {
        val response = client.get("http://localhost:8000")
        assert(response.status.value in 200..299)
        val body: String = response.body()
        assert(body.isNotEmpty())
    }

    @Test
    fun postGoodTest() {
        val instruction = "created,s0,1721604514000"
        val observer = ObserverTestHelper()
        observer.hook = {
            assertEquals(instruction, server.nextInstruction)
        }
        server.subscribe(observer)
        runBlocking {
            client.submitForm("http://localhost:8000/update",
                formParameters = parameters { append("instuction", instruction) })
        }
    }

    @Test
    fun postBadTest() {
        runBlocking {
            val before = server.nextInstruction
            client.submitForm("http://localhost:8000/update")
            assertEquals(before, server.nextInstruction)
        }
    }

    @Test
    fun multiStartTest() {
        assertDoesNotThrow {
            server.start()
            server.start()
            server.start()
        }
    }

    @Test
    fun stopTest() {
        assertDoesNotThrow {
            server.stop()
            server.stop()
            server.start()
        }
    }
}