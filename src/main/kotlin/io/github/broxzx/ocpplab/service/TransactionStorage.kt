package io.github.broxzx.ocpplab.service

import io.github.broxzx.ocpplab.model.Transaction
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class TransactionStorage {
    private val openTransactionsMap = ConcurrentHashMap<Int, Transaction>()


    fun add(transactionId: Int, transaction: Transaction) {
        openTransactionsMap[transactionId] = transaction
    }

}