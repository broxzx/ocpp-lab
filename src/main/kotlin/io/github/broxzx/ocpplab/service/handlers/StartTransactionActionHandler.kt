package io.github.broxzx.ocpplab.service.handlers

import io.github.broxzx.ocpplab.model.MessagePayload
import io.github.broxzx.ocpplab.model.Operation
import io.github.broxzx.ocpplab.model.Transaction
import io.github.broxzx.ocpplab.model.request.StartTransactionRequest
import io.github.broxzx.ocpplab.model.response.StartTransactionResponse
import io.github.broxzx.ocpplab.service.TransactionStorage
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import tools.jackson.module.kotlin.convertValue
import kotlin.random.Random

@Component
class StartTransactionActionHandler(val transactionStorage: TransactionStorage) : ActionHandler {


    override fun action(): Operation = Operation.START_TRANSACTION

    override fun type(): Int = 2

    override fun handle(
        session: WebSocketSession,
        messagePayload: MessagePayload
    ) {
        val startTransactionRequest = mapper.convertValue<StartTransactionRequest>(messagePayload.payload)
        val transactionId = Random.nextInt(1, 100_000)
        transactionStorage.add(transactionId, Transaction(transactionId, startTransactionRequest.meterStart))

        ActionHandlerHelper.sendResult(
            session, messagePayload.id,
            StartTransactionResponse(
                StartTransactionResponse.IdTagInfo("", "Accepted"),
                transactionId
            )
        )
    }
}