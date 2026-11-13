package io.github.broxzx.ocpplab.service.handlers

import io.github.broxzx.ocpplab.model.MessagePayload
import io.github.broxzx.ocpplab.model.Operation
import io.github.broxzx.ocpplab.model.response.AuthorizeResponse
import io.github.broxzx.ocpplab.model.response.IdTagInfo
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession

@Component
class AuthorizeActionHandler : ActionHandler {
    override fun action(): Operation = Operation.AUTHORIZE

    override fun handle(
        session: WebSocketSession,
        messagePayload: MessagePayload
    ) {
        ActionHandlerHelper.sendResult(
            session,
            messagePayload.id,
            AuthorizeResponse(IdTagInfo("Accepted"))
        )

    }
}