package io.github.broxzx.ocpplab.model.request

data class MeterValuesRequest(val connectorId: Int, val transactionId: Int, val meterValue: List<MeterValue>) {
    data class MeterValue(val timestamp: String, val sampledValue: List<SampleValue>)

    data class SampleValue(val value: String, val measurand: String, val unit: String)
}