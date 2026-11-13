package io.github.broxzx.ocpplab.service.handlers

import io.github.broxzx.ocpplab.model.MessagePayload
import io.github.broxzx.ocpplab.model.Operation
import io.github.broxzx.ocpplab.model.response.HeartbeatResponse
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import java.time.Instant

@Component
class HeartbeatActionHandler : ActionHandler {
    override fun action(): Operation = Operation.HEART_BEAT

    override fun handle(
        session: WebSocketSession,
        messagePayload: MessagePayload
    ) {
        ActionHandlerHelper.sendResult(
            session, messagePayload.id,
            HeartbeatResponse(Instant.now().toString())
        )
    }
}