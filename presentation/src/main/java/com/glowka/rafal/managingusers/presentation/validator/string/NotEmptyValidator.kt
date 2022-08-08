package com.glowka.rafal.managingusers.presentation.validator.string

import com.glowka.rafal.managingusers.presentation.validator.ValidationResult
import com.glowka.rafal.managingusers.presentation.validator.Validator

class NotEmptyValidator(
  private val errorMessage: String,
  onShowErrorMessage: (String?) -> Unit,
) : Validator<String>(onShowErrorMessage) {

  override fun validateData(data: String): ValidationResult<String> {
    if (data.isEmpty()) return ValidationResult.Error(errorMessage)
    return ValidationResult.Success(data = data)
  }

}