package br.com.zupacademy.lincon.keymanager

import br.com.zupacademy.lincon.KeymanagerRemoveServiceGrpc
import br.com.zupacademy.lincon.RemoveChavePixResponse
import br.com.zupacademy.lincon.keymanager.shared.grpc.KeyManagerGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
class RemoveChavePixControllerTest {

  @field:Inject
  lateinit var removeStub: KeymanagerRemoveServiceGrpc.KeymanagerRemoveServiceBlockingStub

  @field:Inject
  @field:Client("/")
  lateinit var client: HttpClient

  @Factory
  @Replaces(factory = KeyManagerGrpcFactory::class)
  internal class RemoveStubFactory {
    @Singleton
    fun deleteChave() = Mockito.mock(KeymanagerRemoveServiceGrpc.KeymanagerRemoveServiceBlockingStub::class.java)
  }

  @Test
  internal fun `deve remover uma chave pix existente`() {
    val clienteId = UUID.randomUUID().toString()
    val pixId = UUID.randomUUID().toString()

    val respostaGrpc = RemoveChavePixResponse.newBuilder()
      .setClienteId(clienteId)
      .setPixId(pixId)
      .build()

    BDDMockito.given(removeStub.remove(Mockito.any())).willReturn(respostaGrpc)


    val request = HttpRequest.DELETE<Any>("/api/v1/clientes/$clienteId/pix/$pixId")
    val response = client.toBlocking().exchange(request, Any::class.java)

    Assertions.assertEquals(HttpStatus.OK, response.status)
  }

}