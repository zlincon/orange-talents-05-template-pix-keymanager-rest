package br.com.zupacademy.lincon.keymanager

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TipoDeChaveRequestTest {

  @Nested
  inner class  ChaveAleatoriaTest {
    @Test
    fun `deve ser valido quando chave aleatoria for nula ou vazia`() {
      val tipoDeChave = TipoDeChaveRequest.ALEATORIA

      Assertions.assertTrue(tipoDeChave.valida(null))
      Assertions.assertTrue(tipoDeChave.valida(""))
    }

    @Test
    fun `nao deve ser valido quando chave aleatoria possuir um valor`() {
      val tipoDeChave = TipoDeChaveRequest.ALEATORIA

      Assertions.assertFalse(tipoDeChave.valida("um valor qualquer"))
    }
  }

  @Nested
  inner class CpfTest {
    @Test
    fun `deve se valido quando cpf for um nymero valido`() {
      val tipoDeChave = TipoDeChaveRequest.CPF

      Assertions.assertTrue(tipoDeChave.valida("35060731332"))
    }

    @Test
    fun `nao deve ser valido quando cpf for um numero invalido`() {
      val tipoDeChave = TipoDeChaveRequest.CPF

      Assertions.assertFalse(tipoDeChave.valida("35060731331"))
    }

    @Test
    fun `nao deve ser valido quando cpf noa for informado`() {
      val tipoDeChave = TipoDeChaveRequest.CPF

      Assertions.assertFalse(tipoDeChave.valida(null))
      Assertions.assertFalse(tipoDeChave.valida(""))
    }
  }

  @Nested
  inner class CelularTest {
    @Test
    fun `deve ser valido quando celular for um numero valido`() {
      val tipodeDeChave = TipoDeChaveRequest.CELULAR

      Assertions.assertTrue(tipodeDeChave.valida("+5511987654321"))
    }

    @Test
    fun `nao deve ser valido quando celular for um numero invalido`(){
      val tipoDeChave = TipoDeChaveRequest.CELULAR

      Assertions.assertFalse(tipoDeChave.valida("11987654321"))
      Assertions.assertFalse(tipoDeChave.valida("+55a11987654321"))
    }

    @Test
    fun `nao deve ser valido quando celular for um numero nao informado`() {
      val tipoDeChave = TipoDeChaveRequest.CELULAR

      Assertions.assertFalse(tipoDeChave.valida(null))
      Assertions.assertFalse(tipoDeChave.valida(""))
    }

  }

  @Nested
  inner class EmailTest {
    @Test
    fun `deve ser valido quando email for endereco valido`() {
      Assertions.assertTrue(TipoDeChaveRequest.EMAIL.valida("zup.edu@zup.com.br"))
    }

    @Test
    fun `nao deve ser valido quando email estiver em um formato invalido`() {
      Assertions.assertFalse(TipoDeChaveRequest.EMAIL.valida("zup.eduzup.com.br"))
      Assertions.assertFalse(TipoDeChaveRequest.EMAIL.valida("zup.edu@zup.com."))
    }

    @Test
    fun `nao deve ser valido quando email nao for informado`() {
      Assertions.assertFalse(TipoDeChaveRequest.EMAIL.valida(null))
      Assertions.assertFalse(TipoDeChaveRequest.EMAIL.valida(""))
    }
  }
}