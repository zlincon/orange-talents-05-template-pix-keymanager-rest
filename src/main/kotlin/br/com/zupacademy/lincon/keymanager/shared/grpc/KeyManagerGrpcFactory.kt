package br.com.zupacademy.lincon.keymanager.shared.grpc

import br.com.zupacademy.lincon.KeyManagerListaServiceGrpc
import br.com.zupacademy.lincon.KeyManagerRegistraServiceGrpc
import br.com.zupacademy.lincon.KeymanagerCarregaServiceGrpc
import br.com.zupacademy.lincon.KeymanagerRemoveServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerGrpcFactory(@GrpcChannel("keyManager") val channel: ManagedChannel) {

  @Singleton
  fun registraChave() = KeyManagerRegistraServiceGrpc.newBlockingStub(channel)

  @Singleton
  fun deletaChave() = KeymanagerRemoveServiceGrpc.newBlockingStub(channel)

  @Singleton
  fun listaChaves() = KeyManagerListaServiceGrpc.newBlockingStub(channel)

  @Singleton
  fun carregaChave() = KeymanagerCarregaServiceGrpc.newBlockingStub(channel)

}