package io.github.broxzx.ocpplab.model

enum class Operation {

    BOOT_NOTIFICATION("BootNotification"),
    HEART_BEAT("Heartbeat"),
    STATUS_NOTIFICATION("StatusNotification"),
    AUTHORIZE("Authorize"),
    START_TRANSACTION("StartTransaction"),
    METER_VALUES("MeterValues"),
    STOP_TRANSACTION("StopTransaction");


    constructor(operationValue: String) {
        this.operationValue = operationValue
    }

    val operationValue: String


}