package io.github.broxzx.ocpplab.ocpp

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.SubProtocolCapable
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import tools.jackson.module.kotlin.jacksonObjectMapper
import java.time.Instant

@Component
class OcppHandler : TextWebSocketHandler(), SubProtocolCapable {

    private val mapper = jacksonObjectMapper()
    private val log = LoggerFactory.getLogger(OcppHandler::class.java)

    override fun getSubProtocols(): List<String> {
        return listOf("ocpp1.6")
    }


    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val node = mapper.readTree(message.payload)
        val type = node[0].asInt()
        val id = node[1].asString()
        val action = node[2].asString()
        val payload = node[3]

        when (type) {
            2 -> {
                when (action) {
                    "BootNotification" -> {
                        sendResult(
                            session, id, BootNotificationResponse(
                                "Accepted",
                                Instant.now().toString(),
                                60
                            )
                        )
                    }

                    "Heartbeat" -> {
                        sendResult(session, id, HeartbeatResponse(Instant.now().toString()))
                    }

                    else -> {
                        sendError(
                            session, id, "NotImplemented",
                            "Requested Action is not supported by the receiver"
                        )
                    }
                }
            }
        }
    }

    private fun sendResult(session: WebSocketSession, id: String, payload: Any) =
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


    private fun sendError(session: WebSocketSession, id: String, code: String, description: String) =
        session.sendMessage(
            TextMessage(mapper.writeValueAsString(listOf(4, id, code, description, emptyMap<String, Any>())))
        )

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val chargePointId = session.uri?.path?.substringAfterLast('/')
            ?: return session.close(CloseStatus.POLICY_VIOLATION)
        session.attributes["chargePointId"] = chargePointId

        log.info("Connection established: {}", chargePointId)
    }
}