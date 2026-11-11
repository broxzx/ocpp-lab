package io.github.broxzx.ocpplab

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OcppLabApplication

fun main(args: Array<String>) {
    runApplication<OcppLabApplication>(*args)
}
