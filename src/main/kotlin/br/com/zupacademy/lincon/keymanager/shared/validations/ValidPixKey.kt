package br.com.zupacademy.lincon.keymanager.shared.validations

import br.com.zupacademy.lincon.keymanager.NovaChavePixRequest
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidPixKeyValidator::class])
annotation class ValidPixKey(
  val message: String = "chave Pix inválida (\${validatedValue.tipoDeChave})",
  val groups: Array<KClass<Any>> = [],
  val payload: Array<KClass<Payload>> = []
) {
}

@Singleton
class ValidPixKeyValidator : ConstraintValidator<ValidPixKey, NovaChavePixRequest> {
  override fun isValid(
    value: NovaChavePixRequest?,
    annotationMetadata: AnnotationValue<ValidPixKey>,
    context: ConstraintValidatorContext
  ): Boolean {
    if(value?.tipoDeChave == null) {
      return false
    }

    return value.tipoDeChave.valida(value.chave)
  }
}
