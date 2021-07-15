package br.com.zupacademy.lincon

import io.micronaut.runtime.Micronaut.build
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
  info = Info(
    title = "pix-keymanager-rest",
    version = "0.0"
  )
)
object Api {
}

fun main(args: Array<String>) {
  build()
    .args(*args)
    .packages("br.com.zupacademy.lincon")
    .start()
}

