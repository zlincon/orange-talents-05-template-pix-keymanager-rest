package br.com.zupacademy.lincon.keymanager

import br.com.zupacademy.lincon.CarregaChavePixRequest
import br.com.zupacademy.lincon.KeyManagerListaServiceGrpc
import br.com.zupacademy.lincon.KeymanagerCarregaServiceGrpc
import br.com.zupacademy.lincon.ListaChavesPixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/api/v1/clientes/{clienteId}")
class CarregaChavePixController(
  val carregaChavePixClient: KeymanagerCarregaServiceGrpc.KeymanagerCarregaServiceBlockingStub,
  val listaChavesPixClient: KeyManagerListaServiceGrpc.KeyManagerListaServiceBlockingStub
) {

  private val LOGGER = LoggerFactory.getLogger(this::class.java)

  @Get("/pix/{pixId}")
  fun carrega(clienteId: UUID, pixId: UUID): HttpResponse<Any> {
    LOGGER.info("[$clienteId] carrega chave pix por id: $pixId")
    val chaveResponse = carregaChavePixClient.carrega(
      CarregaChavePixRequest.newBuilder()
        .setPixId(
          CarregaChavePixRequest.FiltroPorPixId.newBuilder()
            .setClienteId(clienteId.toString())
            .setPixId(pixId.toString())
            .build()
        )
        .build()
    )

    return HttpResponse.ok(DetalheChavePixResponse(chaveResponse))
  }

  @Get("/pix/")
  fun lista(clienteId: UUID): HttpResponse<Any> {
    LOGGER.info("[$clienteId] listando chaves pix")
    val pix = listaChavesPixClient.lista(
      ListaChavesPixRequest.newBuilder()
        .setClienteId(clienteId.toString())
        .build()
    )

    val chaves = pix.chavesList.map {
      ChavePixResponse(it)
    }

    return HttpResponse.ok(chaves)
  }


}