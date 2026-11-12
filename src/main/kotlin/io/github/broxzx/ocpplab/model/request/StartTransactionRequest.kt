package io.github.broxzx.ocpplab.model.request

data class StartTransactionRequest(val connectorId: Int, val idTag: String, val meterStart: Int, val timestamp: String)