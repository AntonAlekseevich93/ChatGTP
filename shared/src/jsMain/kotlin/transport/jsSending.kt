package transport

import data.MessageVo
import io.ktor.client.*
import kotlinx.serialization.json.Json
import org.w3c.dom.WebSocket
import org.w3c.workers.ServiceWorkerGlobalScope
import kotlin.js.Date

actual fun getTimeNow(): String = Date().toTimeString()

external val self: ServiceWorkerGlobalScope

actual val localHost: String = "0.0.0.0"

actual suspend fun webSocketSession(client: HttpClient, path: String, onMessageReceive: (MessageVo) -> Unit): WsSession? {
    val ws = WebSocket("ws://$localHost:8080/$path")
    ws.onopen = {

    }
    ws.onmessage = { event ->
        try {
            console.log(event.data)
            event.data?.let {
                onMessageReceive(Json.decodeFromString(MessageVo.serializer(), event.data as String))
            }
            console.log(event.data)
        } catch (e: Exception) {
            console.log("Error: $e.message")
        }
    }
    return ws
}

actual suspend fun WsSession?.sendMessage(message: MessageVo) {
    (this as WebSocket?)?.send(Json.encodeToString(MessageVo.serializer(), message))
}