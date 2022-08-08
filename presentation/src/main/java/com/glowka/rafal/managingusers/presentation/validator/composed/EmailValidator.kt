package com.glowka.rafal.managingusers.presentation.validator.composed

import com.glowka.rafal.managingusers.presentation.validator.ComposeValidator
import com.glowka.rafal.managingusers.presentation.validator.string.NotEmptyValidator
import com.glowka.rafal.managingusers.presentation.validator.string.RegExpValidator
import java.util.regex.Pattern

class EmailValidator(
  emptyStringMessage: String,
  invalidEmailMessage: String,
  onShowErrorMessage: (String?) -> Unit,
) : ComposeValidator<String>(
  listOf(
    NotEmptyValidator(emptyStringMessage, onShowErrorMessage),
    RegExpValidator(REGEX_RULE, invalidEmailMessage, onShowErrorMessage)
  )
) {

  companion object {
    val REGEX_RULE = Pattern.compile(
      "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
          "\\@" +
          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
          "(" +
          "\\." +
          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
          ")+"
    ).toRegex()
  }
}