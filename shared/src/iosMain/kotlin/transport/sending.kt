package transport

import data.MessageVo
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext

actual fun getTimeNow(): String = ""

actual val localHost: String = "localhost"

actual suspend fun webSocketSession(client: HttpClient, path: String, onMessageReceive: (MessageVo) -> Unit): WsSession? {
    var session: DefaultClientWebSocketSession? = null
    withContext(Dispatchers.Default) {
        try {
            client.webSocket(method = HttpMethod.Get, host = localHost, port = 8082, path = path) {
                session = this
                while (true) {
                    this.ensureActive()
                }
            }
        } catch (e: io.ktor.client.engine.darwin.DarwinHttpRequestException) {
            println("Failed to connect to websocket on $localHost! Session not initialized.")
        }
    }
    return session
}

actual suspend fun WsSession?.sendMessage(message: MessageVo) {
    (this as DefaultClientWebSocketSession?)?.sendSerialized(message)
}

