package com.glowka.rafal.managingusers.presentation.validator.composed

import com.glowka.rafal.managingusers.presentation.validator.ComposeValidator
import com.glowka.rafal.managingusers.presentation.validator.string.LengthValidator

class UserNameValidator(
  tooShortMessage: String,
  tooLongMessage: String,
  onShowErrorMessage: (String?) -> Unit,
) : ComposeValidator<String>(
  listOf(
    LengthValidator(3, tooShortMessage, 30, tooLongMessage, onShowErrorMessage)
  ),
)