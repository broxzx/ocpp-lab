package io.github.broxzx.ocpplab.service.handlers

import io.github.broxzx.ocpplab.model.MessagePayload
import io.github.broxzx.ocpplab.model.Operation
import org.springframework.web.socket.WebSocketSession
import tools.jackson.databind.ObjectMapper
import tools.jackson.module.kotlin.jacksonObjectMapper

interface ActionHandler {

    val mapper: ObjectMapper
        get() = jacksonObjectMapper()


    fun action(): Operation

    fun type(): Int

    fun handle(session: WebSocketSession, messagePayload: MessagePayload)
}