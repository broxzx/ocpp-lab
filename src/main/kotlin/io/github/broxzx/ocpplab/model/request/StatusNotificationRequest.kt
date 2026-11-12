package io.github.broxzx.ocpplab.model.request

data class StatusNotificationRequest(val connectorId: Int, val errorCode: String, val status: String)