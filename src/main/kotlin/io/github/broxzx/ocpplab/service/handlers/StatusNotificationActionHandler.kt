package io.github.broxzx.ocpplab.service.handlers

import io.github.broxzx.ocpplab.model.MessagePayload
import io.github.broxzx.ocpplab.model.Operation
import io.github.broxzx.ocpplab.model.request.StatusNotificationRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import tools.jackson.module.kotlin.convertValue

@Component
class StatusNotificationActionHandler : ActionHandler {

    private val log = LoggerFactory.getLogger(this::class.java)


    override fun action(): Operation = Operation.STATUS_NOTIFICATION

    override fun type(): Int = 2

    override fun handle(
        session: WebSocketSession,
        messagePayload: MessagePayload
    ) {
        val statusNotificationRequest = mapper.convertValue<StatusNotificationRequest>(messagePayload.payload)
        log.info(
            "[StatusNotification]: station '{}' status '{}'",
            session.attributes["chargePointId"],
            statusNotificationRequest.status
        )

        ActionHandlerHelper.sendResult(
            session, messagePayload.id,
            emptyMap<String, Any>()
        )
    }
}