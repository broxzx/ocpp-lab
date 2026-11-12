package io.github.broxzx.ocpplab.service.handlers

import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import tools.jackson.module.kotlin.jacksonObjectMapper

class ActionHandlerHelper {

    companion object {
        val mapper = jacksonObjectMapper()

        fun sendResult(session: WebSocketSession, id: String, payload: Any) =
            session.sendMessage(
                TextMessage(
                    mapper.writeValueAsString(
                        listOf(
                            3, id,
                            payload
                        )
                    )
                )
            )


        fun sendError(session: WebSocketSession, id: String, code: String, description: String) =
            session.sendMessage(
                TextMessage(mapper.writeValueAsString(listOf(4, id, code, description, emptyMap<String, Any>())))
            )
    }
}