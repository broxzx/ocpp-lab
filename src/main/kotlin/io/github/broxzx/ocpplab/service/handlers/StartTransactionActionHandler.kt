package io.github.broxzx.ocpplab.service.handlers

import io.github.broxzx.ocpplab.model.MessagePayload
import io.github.broxzx.ocpplab.model.Operation
import io.github.broxzx.ocpplab.model.Transaction
import io.github.broxzx.ocpplab.model.request.StartTransactionRequest
import io.github.broxzx.ocpplab.model.response.IdTagInfo
import io.github.broxzx.ocpplab.model.response.StartTransactionResponse
import io.github.broxzx.ocpplab.service.TransactionStorage
import io.github.broxzx.ocpplab.util.Json
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import tools.jackson.module.kotlin.convertValue
import java.util.concurrent.atomic.AtomicInteger

@Component
class StartTransactionActionHandler(val transactionStorage: TransactionStorage) : ActionHandler {

    private val atomicInteger = AtomicInteger()

    override fun action(): Operation = Operation.START_TRANSACTION

    override fun handle(
        session: WebSocketSession,
        messagePayload: MessagePayload
    ) {
        val startTxRequest = Json.mapper.convertValue<StartTransactionRequest>(messagePayload.payload)
        val txId = nextId()
        transactionStorage.add(txId, Transaction(txId, startTxRequest.meterStart))

        ActionHandlerHelper.sendResult(
            session, messagePayload.id,
            StartTransactionResponse(
                IdTagInfo("", "Accepted"),
                txId
            )
        )
    }

    private fun nextId(): Int {
        return atomicInteger.getAndIncrement()
    }
}