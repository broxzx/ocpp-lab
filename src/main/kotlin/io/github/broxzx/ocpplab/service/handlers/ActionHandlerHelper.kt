package io.github.broxzx.ocpplab.service.handlers

import io.github.broxzx.ocpplab.util.Json
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import tools.jackson.module.kotlin.jacksonObjectMapper

class ActionHandlerHelper {

    companion object {
        fun sendResult(session: WebSocketSession, id: String, payload: Any) =
            session.sendMessage(
                TextMessage(
                    Json.mapper.writeValueAsString(
                        listOf(
                            3, id,
                            payload
                        )
                    )
                )
            )


        fun sendError(session: WebSocketSession, id: String, code: String, description: String) =
            session.sendMessage(
                TextMessage(Json.mapper.writeValueAsString(listOf(4, id, code, description, emptyMap<String, Any>())))
            )
    }
}