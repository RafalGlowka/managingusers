package com.glowka.rafal.managingusers.presentation.validator

fun <T1 : Any, T2 : Any, T3 : Any> allSuccess(
  result1: ValidationResult<T1>,
  result2: ValidationResult<T2>,
  result3: ValidationResult<T3>,
  onSuccessCall: (T1, T2, T3) -> Unit
): Boolean {
  if (result1 is ValidationResult.Error) {
    println("error: ${result1.message}")
    return false
  }
  if (result2 is ValidationResult.Error) {
    println("error: ${result2.message}")
    return false
  }
  if (result3 is ValidationResult.Error) {
    println("error: ${result3.message}")
    return false
  }
  onSuccessCall(
    (result1 as ValidationResult.Success<T1>).data,
    (result2 as ValidationResult.Success<T2>).data,
    (result3 as ValidationResult.Success<T3>).data
  )
  return true
}

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any> allSuccess(
  result1: ValidationResult<T1>,
  result2: ValidationResult<T2>,
  result3: ValidationResult<T3>,
  result4: ValidationResult<T4>,
  onSuccessCall: (T1, T2, T3, T4) -> Unit
): Boolean {
  if (result1 is ValidationResult.Error) {
    return false
  }
  if (result2 is ValidationResult.Error) {
    return false
  }
  if (result3 is ValidationResult.Error) {
    return false
  }
  if (result4 is ValidationResult.Error) {
    return false
  }
  onSuccessCall(
    (result1 as ValidationResult.Success<T1>).data,
    (result2 as ValidationResult.Success<T2>).data,
    (result3 as ValidationResult.Success<T3>).data,
    (result4 as ValidationResult.Success<T4>).data
  )
  return true
}