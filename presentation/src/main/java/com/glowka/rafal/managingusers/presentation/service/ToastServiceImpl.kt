package com.glowka.rafal.managingusers.presentation.service

import android.content.Context
import android.widget.Toast
import com.glowka.rafal.managingusers.domain.service.ToastService

class ToastServiceImpl(
  val context: Context,
) : ToastService {
  override fun showMessage(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
  }
}