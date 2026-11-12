package io.github.broxzx.ocpplab.service

import io.github.broxzx.ocpplab.model.MessagePayload
import io.github.broxzx.ocpplab.model.Operation
import io.github.broxzx.ocpplab.service.handlers.ActionHandler
import io.github.broxzx.ocpplab.service.handlers.ActionHandlerHelper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.SubProtocolCapable
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import tools.jackson.module.kotlin.jacksonObjectMapper

@Component
class OcppHandler(handlers: List<ActionHandler>) : TextWebSocketHandler(), SubProtocolCapable {

    private val mapper = jacksonObjectMapper()
    private val log = LoggerFactory.getLogger(OcppHandler::class.java)

    private val handlers = handlers.associateBy { it.action() }
    private val operations = Operation.entries.associateBy { it.operationValue }

    override fun getSubProtocols(): List<String> {
        return listOf("ocpp1.6")
    }


    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val node = mapper.readTree(message.payload)
        val type = node[0].asInt()
        val id = node[1].asString()
        val action = node[2].asString()
        val payload = node[3]

        val messagePayload = MessagePayload(type, id, action, payload)

        when (type) {
            2 -> {
                val handler: ActionHandler? = handlers[operations[action]]
                if (handler == null) ActionHandlerHelper.sendError(session, id, "NotImplemented", "...")
                else handler.handle(session, messagePayload)
            }
        }
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val chargePointId = session.uri?.path?.substringAfterLast('/')
            ?: return session.close(CloseStatus.POLICY_VIOLATION)
        session.attributes["chargePointId"] = chargePointId

        log.info("Connection established: {}", chargePointId)
    }
}