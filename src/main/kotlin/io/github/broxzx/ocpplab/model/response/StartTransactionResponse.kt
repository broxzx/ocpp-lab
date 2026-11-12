package io.github.broxzx.ocpplab.model.response

data class StartTransactionResponse(val idTagInfo: IdTagInfo, val transactionId: Int) {
    data class IdTagInfo(val parentTagId: String, val status: String)
}