package br.com.zupacademy.lincon.keymanager

import br.com.zupacademy.lincon.KeyManagerRegistraServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.created
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.*
import javax.validation.Valid

@Validated
@Controller("/api/v1/clientes/{clienteId}")
class RegistraChavePixController(private val registraChavePixClient: KeyManagerRegistraServiceGrpc.KeyManagerRegistraServiceBlockingStub) {
  private val LOGGER = LoggerFactory.getLogger(this::class.java)

  @Post("/pix")
  fun create(clienteId: UUID, @Valid @Body request: NovaChavePixRequest): HttpResponse<Any> {
    LOGGER.info("[$clienteId] criando uma nova chave pix com $request")
    val grpcResponse = registraChavePixClient.registra(request.toModelGrpc(clienteId))

    return HttpResponse.created(location(clienteId, grpcResponse.pixId))
  }

  private fun location(clienteId: UUID, pixId: String) = HttpResponse.uri("/api/v1/clientes/$clienteId/pix/${pixId}")
}