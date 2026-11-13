package io.github.broxzx.ocpplab.service.handlers

import io.github.broxzx.ocpplab.model.MessagePayload
import io.github.broxzx.ocpplab.model.Operation
import io.github.broxzx.ocpplab.model.request.StopTransactionRequest
import io.github.broxzx.ocpplab.service.TransactionStorage
import io.github.broxzx.ocpplab.util.Json
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import tools.jackson.module.kotlin.convertValue

@Component
class StopTransactionActionHandler(val transactionStorage: TransactionStorage) : ActionHandler {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun action(): Operation = Operation.STOP_TRANSACTION

    override fun handle(
        session: WebSocketSession,
        messagePayload: MessagePayload
    ) {
        val stopTxRequest = Json.mapper.convertValue<StopTransactionRequest>(messagePayload.payload)
        val tx = transactionStorage.get(stopTxRequest.transactionId)

        if (tx == null) {
            log.error("Transaction id {} not found", stopTxRequest.transactionId)
            return
        }

        val difference = stopTxRequest.meterStop - tx.meterStart
        if (difference < 0) {
            log.error("Difference can't be negative {}", difference)
        }

        log.info("{}: transaction {} finished", session.attributes["chargePointId"], tx.id)
    }
}