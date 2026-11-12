package io.github.broxzx.ocpplab.model

import tools.jackson.databind.JsonNode

data class MessagePayload(
    val type: Int,
    val id: String,
    val action: String,
    val payload: JsonNode
)
