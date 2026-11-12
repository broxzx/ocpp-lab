package io.github.broxzx.ocpplab.service.handlers

import io.github.broxzx.ocpplab.model.MessagePayload
import io.github.broxzx.ocpplab.model.Operation
import io.github.broxzx.ocpplab.model.response.BootNotificationResponse
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import java.time.Instant

@Component
class BootNotificationActionHandler : ActionHandler {

    override fun action(): Operation = Operation.BOOT_NOTIFICATION

    override fun type(): Int = 2

    override fun handle(
        session: WebSocketSession,
        messagePayload: MessagePayload
    ) {
        ActionHandlerHelper.sendResult(
            session, messagePayload.id,
            BootNotificationResponse(
                "Accepted",
                Instant.now().toString(),
                60
            )
        )
    }
}