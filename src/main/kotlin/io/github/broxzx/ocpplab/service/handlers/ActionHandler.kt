package io.github.broxzx.ocpplab.service.handlers

import io.github.broxzx.ocpplab.model.MessagePayload
import io.github.broxzx.ocpplab.model.Operation
import org.springframework.web.socket.WebSocketSession

interface ActionHandler {

    fun action(): Operation

    fun handle(session: WebSocketSession, messagePayload: MessagePayload)
}