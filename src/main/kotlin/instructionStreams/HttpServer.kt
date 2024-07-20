package instructionStreams

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

class HttpServer (private val port: Int): InstructionStream() {
    private lateinit var server: NettyApplicationEngine
    private var running = false

    override fun start() {
        if (running) return
        server = embeddedServer(Netty, port = port) {
            routing {
                get("/") {
                    call.respondFile(File("res/index.html"))
                }
                post("/update") {
                    val updateString = call.receiveParameters()["instruction"]
                    updateString?.let {
                        processInput(it)
                    }
                    call.respondRedirect("/")
                }
            }
        }.start(wait = false)
        running = true
    }

    override fun stop() {
        if (running) {
            server.stop()
            running = false
        }
    }

    private fun processInput(updateString: String) {
        nextInstruction = updateString
        notifySubscribers()
    }
}