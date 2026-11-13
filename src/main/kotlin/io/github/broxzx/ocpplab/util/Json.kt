package io.github.broxzx.ocpplab.util

import tools.jackson.databind.ObjectMapper
import tools.jackson.module.kotlin.jacksonObjectMapper

object Json {
    val mapper: ObjectMapper = jacksonObjectMapper()


}