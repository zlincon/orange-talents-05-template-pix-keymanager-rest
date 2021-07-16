package br.com.zupacademy.lincon.keymanager.shared

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GlobalExceptionHandlerTest {
  val requestGenerica = HttpRequest.GET<Any>("/")

  @Test
  internal fun `deve retornar 404 quando statusException for not found`(){
    val mensagem = "nao encontrado"
    val notFoundException = StatusRuntimeException(Status.NOT_FOUND.withDescription(mensagem))

    val resposta = GlobalExceptionHandler().handle(requestGenerica, notFoundException)

    Assertions.assertEquals(HttpStatus.NOT_FOUND, resposta.status)
    Assertions.assertNotNull(resposta.body())
    Assertions.assertEquals(mensagem, (resposta.body() as JsonError).message)
  }

  @Test
  internal fun `deve retornar 422 quando statusException for already existis`(){
    val mensagem = "chave ja existente"
    val alreadyExistsException = StatusRuntimeException(Status.ALREADY_EXISTS.withDescription(mensagem))

    val resposta = GlobalExceptionHandler().handle(requestGenerica, alreadyExistsException)

    Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, resposta.status)
    Assertions.assertNotNull(resposta.body())
    Assertions.assertEquals(mensagem, (resposta.body() as JsonError).message)
  }

  @Test
  internal fun `deve retornar 400 quando statusException for invalid argument`() {
    val mensagem = "Dados da requisição estão inválidos"
    val invalidArgumentException = StatusRuntimeException(Status.INVALID_ARGUMENT)

    val resposta = GlobalExceptionHandler().handle(requestGenerica, invalidArgumentException)

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, resposta.status)
    Assertions.assertNotNull(resposta.body())
    Assertions.assertEquals(mensagem, (resposta.body() as JsonError).message)
  }

  @Test
  internal fun `deve retornar 500 quando qualquer outro erro for lancado`() {
    val internalException = StatusRuntimeException(Status.INTERNAL)
    val resposta = GlobalExceptionHandler().handle(requestGenerica, internalException)

    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resposta.status)
    Assertions.assertNotNull(resposta.body())
    Assertions.assertTrue((resposta.body() as JsonError).message.contains("INTERNAL"))
  }
}