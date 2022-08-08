package com.glowka.rafal.managingusers.presentation.validator

import java.lang.IllegalArgumentException

open class ComposeValidator<DATA : Any>(
  val validators: List<Validator<DATA>>,
  ) : Validator<DATA>({}) {

  override fun validateData(data: DATA): ValidationResult<DATA> {
    throw IllegalArgumentException("validateData is not supported for compositeValidator")
  }

  override fun validate(data: DATA): ValidationResult<DATA> {
    validators.forEach { validator ->
      val result = validator.validate(data)
      if (result is ValidationResult.Error)
        return result
    }
    return ValidationResult.Success(data = data)
  }
}