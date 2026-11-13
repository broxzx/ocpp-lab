package io.github.broxzx.ocpplab.service.handlers

import io.github.broxzx.ocpplab.model.MessagePayload
import io.github.broxzx.ocpplab.model.Operation
import io.github.broxzx.ocpplab.model.request.MeterValuesRequest
import io.github.broxzx.ocpplab.util.Json
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import tools.jackson.module.kotlin.convertValue

@Component
class MeterValueActionHandler : ActionHandler {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun action(): Operation = Operation.METER_VALUES

    override fun handle(
        session: WebSocketSession,
        messagePayload: MessagePayload
    ) {
        val meterValuesRequest = Json.mapper.convertValue<MeterValuesRequest>(messagePayload.payload)

        meterValuesRequest.meterValue.flatMap { it.sampledValue }.stream()
            .forEach { log.info("Current value: {} {}", it.value, it.unit) }

        ActionHandlerHelper.sendResult(session, messagePayload.id, emptyMap<String, Any>())
    }
}