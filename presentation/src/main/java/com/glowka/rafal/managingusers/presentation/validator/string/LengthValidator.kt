package com.glowka.rafal.managingusers.presentation.validator.string

import com.glowka.rafal.managingusers.presentation.validator.ValidationResult
import com.glowka.rafal.managingusers.presentation.validator.Validator

class LengthValidator(
  val minLength: Int,
  val toShortMessage: String,
  val maxLength: Int,
  val toLongMessage: String,
  onShowErrorMessage: (String?) -> Unit,
) : Validator<String>(onShowErrorMessage) {
  override fun validateData(data: String): ValidationResult<String> {
    if (data.length < minLength) return ValidationResult.Error(toShortMessage)
    if (data.length > maxLength) return ValidationResult.Error(toLongMessage)
    return ValidationResult.Success(data = data)
  }
}