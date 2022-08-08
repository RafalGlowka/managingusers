package com.glowka.rafal.managingusers.presentation.validator.string

import com.glowka.rafal.managingusers.presentation.validator.ValidationResult
import com.glowka.rafal.managingusers.presentation.validator.Validator

class RegExpValidator(
  val regex: Regex,
  private val errorMessage: String,
  onShowErrorMessage: (String?) -> Unit,
) : Validator<String>(onShowErrorMessage) {

  override fun validateData(data: String): ValidationResult<String> {
    if (regex.matches(data).not()) return ValidationResult.Error(errorMessage)
    return ValidationResult.Success(data = data)
  }

}