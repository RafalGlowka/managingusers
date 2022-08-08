package com.glowka.rafal.managingusers.presentation.validator

sealed class ValidationResult<out DATA : Any> {
  data class Error(val message: String) : ValidationResult<Nothing>()
  data class Success<DATA : Any>(val data: DATA) : ValidationResult<DATA>()
}

abstract class Validator<DATATYPE : Any>(
  val onShowErrorMessage: (String?) -> Unit,
) {
  protected abstract fun validateData(data: DATATYPE): ValidationResult<DATATYPE>

  open fun validate(data: DATATYPE): ValidationResult<DATATYPE> {
    val result = validateData(data)
    if (result is ValidationResult.Error) {
      onShowErrorMessage(result.message)
    } else {
      onShowErrorMessage(null)
    }
    return result
  }
}