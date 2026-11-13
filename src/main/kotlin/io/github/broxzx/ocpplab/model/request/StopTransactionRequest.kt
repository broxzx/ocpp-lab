package io.github.broxzx.ocpplab.model.request

data class StopTransactionRequest(val transactionId: Int, val meterStop: Int, val timestamp: String, val reason: String)