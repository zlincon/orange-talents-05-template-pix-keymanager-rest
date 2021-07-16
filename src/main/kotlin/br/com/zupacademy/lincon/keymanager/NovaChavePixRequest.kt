package br.com.zupacademy.lincon.keymanager

import br.com.caelum.stella.validation.CPFValidator
import br.com.zupacademy.lincon.RegistraChavePixRequest
import br.com.zupacademy.lincon.TipoDeChave
import br.com.zupacademy.lincon.TipoDeConta
import br.com.zupacademy.lincon.keymanager.shared.validations.ValidPixKey
import io.micronaut.core.annotation.Introspected
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidPixKey
@Introspected
class NovaChavePixRequest(
  @field:NotNull val tipoDeConta: TipoDeContaRequest?,
  @field:Size(max = 77) val chave: String?,
  @field:NotNull val tipoDeChave: TipoDeChaveRequest?
) {
  fun toModelGrpc(clienteId: UUID): RegistraChavePixRequest {
    return RegistraChavePixRequest.newBuilder()
      .setClienteId(clienteId.toString())
      .setTipoDeConta(tipoDeConta?.atributoGrpc ?: TipoDeConta.UNKNOWN_TIPO_CONTA)
      .setTipoDeChave(tipoDeChave?.atributoGrpc ?: TipoDeChave.UNKNOWN_TIPO_CHAVE)
      .setChave(chave ?: "")
      .build()
  }

}

enum class TipoDeChaveRequest(val atributoGrpc: TipoDeChave) {
  CPF(TipoDeChave.CPF) {
    override fun valida(chave: String?): Boolean {
      if (chave.isNullOrEmpty()) {
        return false
      }

      return CPFValidator(false).invalidMessagesFor(chave)
        .isEmpty()
    }
  },
  CELULAR(TipoDeChave.CELULAR){
    override fun valida(chave: String?): Boolean {
      if(chave.isNullOrBlank()) return false

      return chave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
    }
  },
  EMAIL(TipoDeChave.EMAIL){
    override fun valida(chave: String?): Boolean {
      if (chave.isNullOrBlank()) {
        return false
      }

      return EmailValidator().run {
        initialize(null)
        isValid(chave, null)
      }
    }
  },
  ALEATORIA(TipoDeChave.ALEATORIA) {
    override fun valida(chave: String?): Boolean = chave.isNullOrBlank()
  }

  ;

  abstract fun valida(chave: String?): Boolean
}

enum class TipoDeContaRequest(val atributoGrpc: TipoDeConta) {
  CONTA_CORRENTE(TipoDeConta.CONTA_CORRENTE),
  CONTA_POUPANCA(TipoDeConta.CONTA_POUPANCA)
}
