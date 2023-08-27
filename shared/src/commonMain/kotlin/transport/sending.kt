package transport

import data.MessageVo
import io.ktor.client.*

expect fun getTimeNow(): String

expect val localHost: String

typealias WsSession = Any

expect suspend fun webSocketSession(client: HttpClient, path: String, onMessageReceive: (MessageVo) -> Unit = {}): WsSession?

expect suspend fun WsSession?.sendMessage(message: MessageVo)